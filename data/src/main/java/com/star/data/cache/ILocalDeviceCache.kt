package com.star.data.cache

import android.content.SharedPreferences
import com.star.data.customconst.PrefsConst.App.DEVICE_ID
import com.star.data.customconst.PrefsConst.App.DEVICE_INFO_CONTENT
import com.star.data.db.device.DeviceDatabase
import com.star.extension.get
import com.star.extension.set
import io.reactivex.rxjava3.core.Observable

interface ILocalDeviceCache {
    fun saveDeviceInfo(content: String)
    fun getDeviceInfo(): Observable<String>
    fun saveDeviceId(id: Int)
}

class LocalDeviceCache(
    private val appPreferences: SharedPreferences,
    private val deviceDatabase: DeviceDatabase
) : ILocalDeviceCache {
    override fun saveDeviceInfo(content: String) {
        appPreferences[DEVICE_INFO_CONTENT] = content
    }

    override fun getDeviceInfo(): Observable<String> =
        Observable.just(appPreferences[DEVICE_INFO_CONTENT, ""] ?: "")

    override fun saveDeviceId(id: Int) {
        appPreferences[DEVICE_ID] = id.toString()
    }
}