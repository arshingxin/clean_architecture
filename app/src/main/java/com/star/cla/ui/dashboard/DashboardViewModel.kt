package com.star.cla.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.config.AppConfig
import com.star.domain.usecase.IDeviceInfoUseCase
import com.star.extension.log.logStar
import com.star.extension.toJson
import io.reactivex.rxjava3.schedulers.Schedulers

class DashboardViewModel(private val deviceInfoUseCase: IDeviceInfoUseCase) : AutoDisposeViewModel() {
    private val TAG = DashboardViewModel::class.java.simpleName
    private val DEBUG = true
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
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
            .map {
                if (it.id == -1) {
                    if (DEBUG) logStar(TAG, "remote device info:發生錯誤")
                } else {
                    if (DEBUG) logStar(TAG, "remote device info:${it.toJson()}")
                }
            }
            .subscribeOn(Schedulers.io())
            .add(key, TAG)
    }

    override fun pause() {
    }

    override fun destroy() {
        onCleared()
    }
}