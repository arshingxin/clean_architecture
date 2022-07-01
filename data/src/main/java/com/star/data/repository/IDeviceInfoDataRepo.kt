package com.star.data.repository

import com.star.data.api.AppApiV2
import com.star.data.api.response.DeviceInfo
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface IDeviceInfoDataRepo {
    fun getDeviceInfo(sn: String): Observable<DeviceInfo>
    fun getDeviceInfoTmp(mac: String): Observable<ResponseBody>
}

class DeviceInfoDataRepo: KoinComponent, IDeviceInfoDataRepo {
    private val appApiV2: AppApiV2 by inject(named(AppApiV2::class.java.simpleName))

    override fun getDeviceInfo(sn: String): Observable<DeviceInfo> = appApiV2.getDeviceInfo(sn)

    override fun getDeviceInfoTmp(mac: String) = appApiV2.getDeviceInfoTmp(mac)
}