package com.star.cla.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.config.AppConfig
import com.star.cla.extension.toJson
import com.star.cla.log.logStar
import com.star.domain.interactor.IDeviceInfoUseCase
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
        deviceInfoUseCase.getRemoteDeviceInfo(AppConfig.Device.SN)
            .map {
                if (DEBUG) logStar(TAG, "getDeviceInfo:${it.toJson()}")
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