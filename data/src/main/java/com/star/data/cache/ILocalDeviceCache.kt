package com.star.data.cache

import android.content.SharedPreferences
import com.star.data.customconst.PrefsConst
import com.star.data.customconst.PrefsConst.App.DEVICE_ID
import com.star.data.customconst.PrefsConst.App.DEVICE_INFO_CONTENT
import com.star.data.db.device.DeviceDatabase
import com.star.extension.get
import com.star.extension.set
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface ILocalDeviceCache {
    fun saveDeviceInfo(content: String)
    fun getDeviceInfo(): Observable<String>
    fun saveDeviceId(id: Int)
    fun getDeviceId(): String
}

class LocalDeviceCache : KoinComponent, ILocalDeviceCache {
    private val appPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))
    private val deviceDatabase: DeviceDatabase by inject()

    override fun saveDeviceInfo(content: String) {
        appPreferences[DEVICE_INFO_CONTENT] = content
    }

    override fun getDeviceInfo(): Observable<String> =
        Observable.just(appPreferences[DEVICE_INFO_CONTENT, ""] ?: "")

    override fun saveDeviceId(id: Int) {
        appPreferences[DEVICE_ID] = id.toString()
    }

    override fun getDeviceId(): String = appPreferences[DEVICE_ID, ""] ?: ""
}