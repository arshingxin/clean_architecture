package com.star.domain.repository

import com.star.data.repository.IDeviceInfoDataRepo
import com.star.domain.interactor.IDeviceInfoUseCase
import com.star.domain.model.DeviceInfoModel
import com.star.domain.model.toDeviceInfoModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io

class DeviceInfoUseCase(private val deviceInfoDataRepo: IDeviceInfoDataRepo) :
    IDeviceInfoUseCase {
    override fun getDeviceInfo(sn: String): Observable<DeviceInfoModel> =
        deviceInfoDataRepo.getDeviceInfo(sn).map { it.toDeviceInfoModel() }.subscribeOn(io())

    override fun getDeviceInfoTmp(sn: String) = deviceInfoDataRepo.getDeviceInfoTmp(sn)
}