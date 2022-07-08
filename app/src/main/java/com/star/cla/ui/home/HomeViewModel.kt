package com.star.cla.ui.home

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.domain.model.DeviceInfoModel
import com.star.domain.usecase.AdInfoUseCase
import com.star.domain.usecase.DeviceInfoUseCase
import org.koin.core.component.inject

open class HomeViewModel : AutoDisposeViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName
    private val DEBUG = true
    val deviceInfoUseCase: DeviceInfoUseCase by inject()
    val adInfoUseCase: AdInfoUseCase by inject()
    val text = MutableLiveData<String>().apply { postValue("This is home Fragment") }
    val deviceInfoModelLiveData = MutableLiveData<ResponseStatus>(ResponseStatus.Loading)

    init {
        resetValue()
    }

    private fun resetValue() {
        // TODO
    }

    sealed class ResponseStatus {
        object Loading : ResponseStatus()
        data class Success(val data: DeviceInfoModel) : ResponseStatus()
        object NetFail : ResponseStatus()
        object Retry : ResponseStatus()
        data class ShowError(val error: String) : ResponseStatus()
        data class Error(val t: Throwable) : ResponseStatus()
    }

    override fun networkConnected() {
        resume()
    }

    override fun resume() {
        var tmpDeviceInfoModel = DeviceInfoModel()
        val key = "resume"
//        deviceInfoModelLiveData.postValue(ResponseStatus.Loading)
//        deviceInfoUseCase.getLocalDeviceInfo()
//            .map {
//                if (it.id == -1) {
//                    if (DEBUG) logStar(TAG, "local device info:未儲存任何資料")
//                    deviceInfoModelLiveData.postValue(ResponseStatus.Retry)
//                } else {
//                    if (DEBUG) logStar(TAG, "local device info:${it.toJson()}")
//                    tmpDeviceInfoModel = it
//                    deviceInfoModelLiveData.postValue(ResponseStatus.Success(it))
//                }
//            }
//            .concatMap {
//                if (!NetStatusBus.peek()) {
//                    deviceInfoModelLiveData.postValue(ResponseStatus.NetFail)
//                    Observable.just(DeviceInfoModel())
//                } else
//                    deviceInfoUseCase.getRemoteDeviceInfo("SMR000275")
//            }
//            .map {
//                if (it.id == -1) {
//                    deviceInfoModelLiveData.postValue(ResponseStatus.ShowError("${if (!NetStatusBus.peek()) "[網路未連線]" else ""}無法取得最新設備資訊!"))
//                    if (DEBUG) {
//                        logStar(TAG, "remote device info:發生錯誤")
//                        logStar(
//                            TAG,
//                            "remote device info:發生錯誤:deviceInfoModelLiveData.value:${deviceInfoModelLiveData.value}"
//                        )
//                        logStar(
//                            TAG,
//                            "remote device info:發生錯誤:tmpDeviceInfoModel:$tmpDeviceInfoModel"
//                        )
//                    }
//                    if (tmpDeviceInfoModel.id == -1)
//                        deviceInfoModelLiveData.postValue(ResponseStatus.Retry)
//                } else {
//                    if (DEBUG) logStar(TAG, "remote device info:${it.toJson()}")
//                    deviceInfoModelLiveData.postValue(ResponseStatus.Success(it))
//                }
//            }
//            .subscribeOn(io())
//            .add(key, TAG)

//        adInfoUseCase.getLocalAdInfoList()
//            .map {
//                if (DEBUG) logStar(TAG, "getLocalAdInfoList: $it")
//            }
//            .concatMap { adInfoUseCase.getRemoteAdInfoList("SMR000275") }
//            .map {
//                if (DEBUG) logStar(TAG, "getRemoteAdInfoList: $it")
//            }
//            .subscribeOn(io())
//            .add(key, TAG)
    }

    override fun pause() {
        // TODO
    }

    override fun destroy() {
        onCleared()
    }
}