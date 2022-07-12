package com.star.cla.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure
import android.text.format.Formatter.formatIpAddress
import android.util.Log
import com.star.cla.BuildConfig
import com.star.extension.log.logStar
import com.star.extension.report
import java.io.BufferedReader
import java.io.File
import java.io.Reader
import java.util.*


private val TAG = "ContextExt"
private val DEBUG = true
const val RC_PERMISSION_MANAGE_OVERLAY_PERMISSION = 1002

fun getAppVersion() = "${getVersionName()}-${BuildConfig.BUILD_TYPE}, ${getDeviceVersion()}"

fun getVersionName() = BuildConfig.VERSION_NAME

fun getVersion() = "version ${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}"

private fun getDeviceVersion(): String = "Android ${Build.VERSION.RELEASE}"

fun Context.getPackageInfo(packageName: String): PackageInfo? {
    return try {
        packageManager.getPackageInfo(packageName, 0)
    } catch (e: Exception) {
        Log.e("ContextExt", "getPackageInfo:: ${e.message}")
        null
    }
}

/**
 * 取得apk檔案的version code
 */
fun Context.getArchiveApkVersionCode(filePath: String): Long =
    packageManager.getPackageArchiveInfo(filePath, 0)?.versionCode?.toLong() ?: -1L

/**
 * 取得已安裝apk的version code
 */
fun Context.getApkVersionCode(packageName: String): Long =
    getPackageInfo(packageName)?.versionCode?.toLong() ?: -1L

fun Context.isApkInstalled(packageName: String) =
    packageManager.getInstalledApplications(0).find { it.packageName == packageName } != null

@SuppressLint("HardwareIds")
fun Context.getAndroidId(): String {
    return Secure.getString(this.contentResolver, Secure.ANDROID_ID) ?: ""
}

fun Context.getSerial(): String {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O ||
        (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
    ) {
        Build.SERIAL.uppercase(Locale.ROOT)
    } else {
        try {
            Build.getSerial().uppercase(Locale.ROOT)
        } catch (ex: java.lang.Exception) {
            ex.report("getSerial")
            getAndroidId()
        }
    }
}

fun Context.getUserId(): String = getAndroidId().substring(0, 8)

fun Context.gotoWriteSystemSetting() {
    val myAppSettings = Intent(
        Settings.ACTION_MANAGE_WRITE_SETTINGS,
        Uri.parse("package:${BuildConfig.APPLICATION_ID}")
    )
    myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
    myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(myAppSettings)
}

fun Activity.startOverlayPermission() {
    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
    intent.data = Uri.parse("package:$packageName")
    startActivityForResult(intent, RC_PERMISSION_MANAGE_OVERLAY_PERMISSION)
}

fun isRooted(): Boolean {
    val buildTags = Build.TAGS
    return if (buildTags != null && buildTags.contains("test-keys")) true
    else {
        var file = File("/system/app/Superuser.apk")
        if (file.exists()) true
        else {
            file = File("/system/xbin/su")
            file.exists()
        }
    }
}

fun Context.getVolumeMax(): Float {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
}

fun Context.getVolume(volume: Int = 0): Float {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    currentVolume += volume
    if (currentVolume <= 0) currentVolume = 0
    else if (currentVolume >= getVolumeMax()) currentVolume = getVolumeMax().toInt()
    return currentVolume.toFloat()
}

fun Context.getCurrentVolume(): Float {
    val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
}

fun Context.setVolume(volume: Int, type: Int = AudioManager.STREAM_MUSIC) {
    if (DEBUG) logStar(TAG, "setVolume:: $volume")
    Log.d(TAG, "setVolume:: $volume")
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    // release 才設定預設音量
    audioManager.setStreamVolume(
        type,
        volume,
        0
    )
}

fun Context.gotoApp(packageName: String) {
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.apply {
        setPackage(null)
        startActivity(this)
    }
}

fun Context.getAllAppList(): List<ResolveInfo> {
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
    return packageManager.queryIntentActivities(mainIntent, 0)
}

fun Context.setScreenBrightness(screenBrightness: Int = getScreenBrightness()) {
    if (!isCanWriteSettings() || screenBrightness == -1) return
    if (DEBUG) logStar(TAG, "setScreenBrightness:$screenBrightness")
    Settings.System.putInt(
        contentResolver,
        Settings.System.SCREEN_BRIGHTNESS,
        screenBrightness
    )
}

fun Context.getScreenBrightness(): Int {
    if (!isCanWriteSettings()) return -1
    return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, -1)
}

fun Context.isCanWriteSettings(): Boolean {
    return Settings.System.canWrite(this)
}

fun Context.getIPAddress(): String {
    val wm: WifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
    return formatIpAddress(wm.connectionInfo.ipAddress)
}

@Suppress("DEPRECATION")
fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == service.name }
}

fun <T> Context.startServiceExt(service: Class<T>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(Intent(this, service))
    } else {
        startService(Intent(this, service))
    }
}

fun Context.getAssetJson(path: String): String {
    if(DEBUG) logStar(TAG, "getAssetJson: $path")
    var content: String
    val inputStream = assets.open(path)
    val reader = BufferedReader(inputStream.reader() as Reader?)
    reader.use { r ->
        content = r.readText()
    }
    return content
}