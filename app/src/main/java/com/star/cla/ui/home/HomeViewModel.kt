package com.star.cla.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.config.AppConfig
import com.star.domain.model.DeviceInfoModel
import com.star.domain.usecase.IDeviceInfoUseCase
import com.star.extension.log.log
import com.star.extension.log.logStar
import com.star.extension.toJson
import io.reactivex.rxjava3.schedulers.Schedulers.io

class HomeViewModel(private val deviceInfoUseCase: IDeviceInfoUseCase) : AutoDisposeViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName
    private val DEBUG = true

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    override fun resume() {
        val key = "resume"
        deviceInfoUseCase.getLocalDeviceInfo()
            .map {
                if (it.id == -1) {
                    if (DEBUG) logStar(TAG, "local device info:未儲存任何資料")
                } else {
                    if (DEBUG) logStar(TAG, "local device info:${it.toJson()}")
                }
            }
            .concatMap { deviceInfoUseCase.getRemoteDeviceInfo(AppConfig.Device.SN) }
            .onErrorReturn {
                it.log(TAG, "remote device info error: ${AppConfig.Device.SN.ifEmpty { "設備SN為空的" }}")
                DeviceInfoModel()
            }
            .map {
                if (it.id == -1) {
                    if (DEBUG) logStar(TAG, "remote device info:發生錯誤")
                } else {
                    if (DEBUG) logStar(TAG, "remote device info:${it.toJson()}")
                }
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun pause() {
        onCleared()
    }

    override fun destroy() {
        onCleared()
    }
}