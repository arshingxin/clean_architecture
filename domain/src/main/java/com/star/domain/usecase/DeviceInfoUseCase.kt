package com.star.domain.usecase

import com.star.data.api.response.DeviceInfo
import com.star.data.cache.ILocalDeviceCache
import com.star.data.repository.IDeviceInfoDataRepo
import com.star.domain.model.DeviceInfoModel
import com.star.domain.model.toDeviceInfoModel
import com.star.extension.report
import com.star.extension.throwException
import com.star.extension.toDataBean
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io

interface IDeviceInfoUseCase {
    fun getRemoteDeviceInfo(sn: String): Observable<DeviceInfoModel>

    fun getLocalDeviceInfo(): Observable<DeviceInfoModel>
}

class DeviceInfoUseCase(
    private val deviceInfoDataRepo: IDeviceInfoDataRepo,
    private val localDeviceCache: ILocalDeviceCache
) : IDeviceInfoUseCase {
    private val TAG = DeviceInfoUseCase::class.java.simpleName
    private val DEBUG = false

    override fun getRemoteDeviceInfo(sn: String): Observable<DeviceInfoModel> =
        deviceInfoDataRepo.getDeviceInfo(sn)
            .map { localDeviceCache.saveDeviceId(it.id) }
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