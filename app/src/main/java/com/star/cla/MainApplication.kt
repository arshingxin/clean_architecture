package com.star.cla

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.star.cla.backforeground.AppLifecycleObserver
import com.star.cla.config.AppConfig
import com.star.cla.di.appModule
import com.star.cla.extension.getSerial
import com.star.cla.extension.toTimeString
import com.star.cla.network.startNetworkMonitor
import com.star.cla.utils.NetUtils
import com.star.data.customconst.PrefsConst
import com.star.extension.config.ExtensionConfig
import com.star.extension.createDirIfNotExists
import com.star.extension.log.TimberLogger
import com.star.extension.log.logError
import com.star.extension.readFileAndContainKeyWord
import com.star.extension.set
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.qualifier.named
import xcrash.ICrashCallback
import xcrash.XCrash
import java.io.File

class MainApplication : MultiDexApplication() {
    private val TAG = MainApplication::class.java.simpleName
    private val DEBUG = true
    private val appSharedPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))

    companion object {
        private var context: Context? = null
        private var mainThreadHandler = Looper.myLooper()?.let { Handler(it) }

        fun getApplicationContext() = context

        fun getSharePreferences(): SharedPreferences? =
            context?.getSharedPreferences(PrefsConst.App.NAME, MODE_PRIVATE)

        fun getExternalFilePath() = context?.getExternalFilesDir(null)?.absolutePath ?: ""

        fun uiThread(block: () -> Unit) {
            mainThreadHandler?.post {
                block.invoke()
            }
        }
    }

    private fun triggerDI() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        val crashPath = "${getExternalFilePath()}/log/crash"
        crashPath.createDirIfNotExists()
        val callback = ICrashCallback { logPath, emergency ->
            val isNative = File(logPath).readFileAndContainKeyWord("Crash type: 'native'")
            logError(TAG, "logPath: $logPath, emergency: $emergency, isNative: $isNative")
            // TODO
        }
        val params = XCrash.InitParameters()
        params.apply {
            setAppVersion(
                "${
                    System.currentTimeMillis().toTimeString("yyyyMMddHHmm")
                }_${BuildConfig.VERSION_NAME}_${BuildConfig.VERSION_CODE}_${BuildConfig.BUILD_TYPE}"
            )
            setNativeCallback(callback)
            setJavaCallback(callback)
            setAnrCallback(callback)
            if (!crashPath.contains("null")) setLogDir(crashPath)
        }
        XCrash.init(this, params)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ExtensionConfig.Path.fileExternalPath = getExternalFilePath()
        ExtensionConfig.appSharedPreferences = getSharePreferences()
        if (BuildConfig.BUILD_TYPE == "release" || !BuildConfig.RUN_TEST) startNetworkMonitor()
        triggerDI()
        AppConfig.Device.MAC = NetUtils.getMacAddress()
        AppConfig.Device.SN = getSerial()
        // 如果SN是空的, SN設為MAC
        if (AppConfig.Device.SN.isEmpty() || AppConfig.Device.SN == AppConfig.Device.DEFAULT_UNKNOWN_SN)
            AppConfig.Device.SN = AppConfig.Device.MAC
        appSharedPreferences[PrefsConst.App.DEVICE_ID] = AppConfig.Device.SN
        TimberLogger().setup(BuildConfig.DEBUG)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }
}