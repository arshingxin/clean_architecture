package com.star.cla.ui.my

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.domain.model.MyItemInfoModel
import com.star.domain.usecase.MyUseCase
import com.star.extension.isJsonArray
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.inject

class MyViewModel : AutoDisposeViewModel() {
    private val TAG = MyViewModel::class.java.simpleName
    private val DEBUG = true
    private val myUseCase: MyUseCase by inject()

    val userNameLiveData = MutableLiveData<String>().apply {
        postValue("東森寵物")
    }
    val userSubNameLiveData = MutableLiveData<String>().apply {
        postValue("集團員工")
    }
    private var _showToastLiveData = MutableLiveData<String>()
    val showToastLiveData get() = _showToastLiveData
    private var _showMyLiveData = MutableLiveData<MutableList<MyItemInfoModel>>()
    val showMyLiveData get() = _showMyLiveData

    override fun init(data: Any?) {
        val key = "init"
        if (data !is String || data.isEmpty() || !data.isJsonArray()) {
            _showToastLiveData.postValue("無法取得個人列表!")
        } else {
            disposableMap[key] = Observable
                .just(true)
                .map {
                    if (DEBUG) logStar(TAG, "$key")
                }
                .concatMap { myUseCase.getRemoteMy() }
                .map {
                    if (DEBUG) logStar(TAG, "$it")
                    it
                }
                .map { _showMyLiveData.postValue(it) }
                .subscribeOn(Schedulers.io())
                .add(key, TAG)
        }
    }

    override fun resume() {

    }

    override fun pause() {
    }

    override fun destroy() {
    }
}