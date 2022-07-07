package com.star.cla.utils

import com.star.extension.equalsIgnoreCase
import com.star.extension.log.logStarError
import java.net.NetworkInterface
import java.util.*

object NetUtils {
    private val TAG = NetUtils::class.java.simpleName
    private val DEBUG = false
    val ERROR_MAC = "02:00:00:00:00:00"

    fun getMacAddress(): String {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equalsIgnoreCase("wlan0")) continue
                val macBytes: ByteArray = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
            logStarError(TAG, ex.message)
        }
        return ERROR_MAC
    }
}