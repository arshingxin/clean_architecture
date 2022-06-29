package com.star.cla.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.star.cla.extension.get
import com.star.cla.extension.md5
import com.star.cla.extension.set
import com.star.data.customconst.PrefsConst
import java.util.*

object DeviceUtils {
    private var uniqueId: String = ""

    @SuppressLint("HardwareIds")
    fun getUniqueId(context: Context): String {
        if (uniqueId.isNotEmpty()) {
            return uniqueId
        }

        val prefsId = PreferenceHelper.appPrefs(context)[PrefsConst.App.DEVICE_ID] ?: ""
        uniqueId = prefsId
        if (uniqueId.isNotEmpty()) {
            return uniqueId
        }

        uniqueId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: ""
        if (uniqueId.isNotEmpty() && uniqueId != "0" && uniqueId != "0123456789ABCDEF" && uniqueId != "9774d56d682e549c") {
            PreferenceHelper.appPrefs(context)[PrefsConst.App.DEVICE_ID] = uniqueId
            return uniqueId
        }

        // generate custom id
        uniqueId = customDeviceId()
        PreferenceHelper.appPrefs(context)[PrefsConst.App.DEVICE_ID] =
                uniqueId
        return uniqueId
    }

    private fun customDeviceId(): String {
        val uuid = UUID.randomUUID().toString()
        val time = System.currentTimeMillis()
        val key = (uuid + time).md5()
        return "custom_$key"
    }
}