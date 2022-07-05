package com.star.domain.usecase

import com.star.data.api.response.DeviceInfo
import com.star.data.cache.LocalDeviceCache
import com.star.data.repository.DeviceInfoDataRepo
import com.star.domain.model.DeviceInfoModel
import com.star.domain.model.toDeviceInfoModel
import com.star.extension.log.log
import com.star.extension.report
import com.star.extension.throwException
import com.star.extension.toDataBean
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface IDeviceInfoUseCase {
    fun getRemoteDeviceInfo(sn: String): Observable<DeviceInfoModel>

    fun getLocalDeviceInfo(): Observable<DeviceInfoModel>
}

open class DeviceInfoUseCase : KoinComponent, IDeviceInfoUseCase {
    private val TAG = DeviceInfoUseCase::class.java.simpleName
    private val DEBUG = false
    private val deviceInfoDataRepo: DeviceInfoDataRepo by inject()
    private val localDeviceCache: LocalDeviceCache by inject()

    override fun getRemoteDeviceInfo(sn: String): Observable<DeviceInfoModel> =
        deviceInfoDataRepo.getDeviceInfo(sn)
            .map {
                localDeviceCache.saveDeviceId(it.id)
                localDeviceCache.saveDeviceInfo(it.toJson())
            }
            .onErrorReturn {
                it.log(TAG, "remote device info error: ${sn.ifEmpty { "設備SN為空的" }}")
                localDeviceCache.saveDeviceId(-1)
                localDeviceCache.saveDeviceInfo("")
                DeviceInfoModel()
            }
            .concatMap { getLocalDeviceInfo() }
            .subscribeOn(io())

    override fun getLocalDeviceInfo(): Observable<DeviceInfoModel> =
        localDeviceCache.getDeviceInfo()
            .map {
                it.toDataBean(DeviceInfo::class.java)?.toDeviceInfoModel()
                    ?: throwException(TAG, "getLocalDeviceInfo error")
            }
            .onErrorReturn {
                it.report(TAG)
                DeviceInfoModel()
            }
            .subscribeOn(io())
}