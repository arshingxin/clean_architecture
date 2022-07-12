package com.star.cla.ui.my.no_read_notification

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io

class NoReadNotificationViewModel: AutoDisposeViewModel() {
    private val TAG = NoReadNotificationViewModel::class.java.simpleName
    private val DEBUG = true
    private var _initLiveData = MutableLiveData<Boolean>()
    val initLiveData get() = _initLiveData

    override fun init(data: Any?) {
        val key = "init"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (DEBUG) logStar(TAG, "$key")
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun resume() {
        val key = "resume"
        disposableMap[key] = Observable
            .just(true)
            .map {
                _initLiveData.postValue(true)
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}