package com.star.cla.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.config.AppConfig
import com.star.cla.extension.toJson
import com.star.cla.log.logStar
import com.star.domain.interactor.IDeviceInfoUseCase
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
                if (DEBUG) logStar(TAG, "local device info:${it.toJson()}")
            }
            .concatMap { deviceInfoUseCase.getRemoteDeviceInfo(AppConfig.Device.SN) }
            .map {
                if (DEBUG) logStar(TAG, "remote device info:${it.toJson()}")
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun pause() {

    }

    override fun destroy() {
        onCleared()
    }
}