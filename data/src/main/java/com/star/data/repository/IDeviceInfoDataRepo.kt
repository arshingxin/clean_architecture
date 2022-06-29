package com.star.data.repository

import com.star.data.api.AppApiV2
import com.star.data.api.response.DeviceInfo
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

interface IDeviceInfoDataRepo {
    fun getDeviceInfo(sn: String): Observable<DeviceInfo>
    fun getDeviceInfoTmp(mac: String): Observable<ResponseBody>
}

class DeviceInfoDataRepo(private val appApiV2: AppApiV2): IDeviceInfoDataRepo {
    override fun getDeviceInfo(sn: String): Observable<DeviceInfo> = appApiV2.getDeviceInfo(sn)

    override fun getDeviceInfoTmp(mac: String) = appApiV2.getDeviceInfoTmp(mac)
}