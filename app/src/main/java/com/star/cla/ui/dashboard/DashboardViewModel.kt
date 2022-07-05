package com.star.cla.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.network.bus.NetStatusBus
import com.star.cla.ui.home.HomeViewModel
import com.star.domain.model.DeviceInfoModel
import com.star.domain.usecase.DeviceInfoUseCase
import com.star.extension.log.logStar
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import org.koin.core.component.inject

class DashboardViewModel : AutoDisposeViewModel() {
    private val TAG = DashboardViewModel::class.java.simpleName
    private val DEBUG = true
    private val deviceInfoUseCase: DeviceInfoUseCase by inject()
    val text = MutableLiveData<String>().apply { postValue("This is dashboard Fragment") }
    val deviceInfoModelLiveData = MutableLiveData<HomeViewModel.ResponseStatus>(HomeViewModel.ResponseStatus.Loading)

    override fun resume() {
        var tmpDeviceInfoModel = DeviceInfoModel()
        val key = "resume"
        deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.Loading)
        deviceInfoUseCase.getLocalDeviceInfo()
            .map {
                if (it.id == -1) {
                    if (DEBUG) logStar(TAG, "local device info:未儲存任何資料")
                    deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.Retry)
                } else {
                    if (DEBUG) logStar(TAG, "local device info:${it.toJson()}")
                    tmpDeviceInfoModel = it
                    deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.Success(it))
                }
            }
            .concatMap {
                if (!NetStatusBus.peek()) {
                    deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.NetFail)
                    Observable.just(DeviceInfoModel())
                } else
                    deviceInfoUseCase.getRemoteDeviceInfo("SMR000275")
            }
            .map {
                if (it.id == -1) {
                    deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.ShowError("${if (!NetStatusBus.peek()) "[網路未連線]" else ""}無法取得最新設備資訊!"))
                    if (DEBUG) {
                        logStar(TAG, "remote device info:發生錯誤")
                        logStar(
                            TAG,
                            "remote device info:發生錯誤:deviceInfoModelLiveData.value:${deviceInfoModelLiveData.value}"
                        )
                        logStar(
                            TAG,
                            "remote device info:發生錯誤:tmpDeviceInfoModel:$tmpDeviceInfoModel"
                        )
                    }
                    if (tmpDeviceInfoModel.id == -1)
                        deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.Retry)
                } else {
                    if (DEBUG) logStar(TAG, "remote device info:${it.toJson()}")
                    deviceInfoModelLiveData.postValue(HomeViewModel.ResponseStatus.Success(it))
                }
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