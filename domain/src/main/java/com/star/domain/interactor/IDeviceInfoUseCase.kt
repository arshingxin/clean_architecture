package com.star.domain.interactor

import com.star.domain.model.DeviceInfoModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

interface IDeviceInfoUseCase {
    fun getDeviceInfo(sn: String): Observable<DeviceInfoModel>

    fun getDeviceInfoTmp(sn: String): Observable<ResponseBody>
}