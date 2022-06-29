package com.star.cla

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.star.cla.bus.ForegroundBackgroundStatus
import com.star.cla.config.AppConfig
import com.star.cla.di.appModule
import com.star.cla.extension.*
import com.star.cla.log.logStar
import com.star.cla.log.logStarError
import com.star.cla.utils.NetUtils
import com.star.data.customconst.PrefsConst
import leakcanary.LeakCanary
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.qualifier.named
import xcrash.ICrashCallback
import xcrash.XCrash
import java.io.File

class MainApplication: MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    private val TAG = MainApplication::class.java.simpleName
    private val DEBUG = false

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false
    private val appSharedPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))

    companion object {
        private lateinit var context: Context
        private var mainThreadHandler = Looper.myLooper()?.let { Handler(it) }
        fun getApplicationContext() = context
        fun getMainThreadHandler() = mainThreadHandler
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
        val crashPath = "${getExternalFilesDir(null)}/log/crash"
        crashPath.createDirIfNotExists()
        val callback = ICrashCallback { logPath, emergency ->
            val isNative = File(logPath).readFileAndContainKeyWord("Crash type: 'native'")
            logStarError("logPath: $logPath, emergency: $emergency, isNative: $isNative")
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
        triggerDI()
        AppConfig.Device.MAC = NetUtils.getMacAddress()
        AppConfig.Device.SN = getSerial()
        // 如果SN是空的, SN設為MAC
        if (AppConfig.Device.SN.isEmpty() || AppConfig.Device.SN == AppConfig.Device.DEFAULT_UNKNOWN_SN)
            AppConfig.Device.SN = AppConfig.Device.MAC
        appSharedPreferences[PrefsConst.App.DEVICE_ID] = AppConfig.Device.SN
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) { }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            if (DEBUG) Log.i(TAG, "onActivityStarted:: Foreground")
            // App enters foreground
            ForegroundBackgroundStatus.setIsBackground(false)
            ForegroundBackgroundStatus.post(ForegroundBackgroundStatus.Status.Foreground)
        }
    }

    override fun onActivityResumed(activity: Activity) { }

    override fun onActivityPaused(activity: Activity) { }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            if (DEBUG) logStar("onActivityStopped:: Background")
            // 離開app將log寫入檔案
            ForegroundBackgroundStatus.setIsBackground(true)
            ForegroundBackgroundStatus.post(ForegroundBackgroundStatus.Status.Background)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { }

    override fun onActivityDestroyed(activity: Activity) { }
}