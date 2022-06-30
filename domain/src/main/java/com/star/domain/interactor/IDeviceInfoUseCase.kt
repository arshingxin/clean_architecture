package com.star.domain.interactor

import com.star.domain.model.DeviceInfoModel
import io.reactivex.rxjava3.core.Observable

interface IDeviceInfoUseCase {
    fun getRemoteDeviceInfo(sn: String): Observable<DeviceInfoModel>

    fun getLocalDeviceInfo(): Observable<DeviceInfoModel>
}