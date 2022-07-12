package com.star.cla.ui.my

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MyViewModel : AutoDisposeViewModel() {
    private val TAG = MyViewModel::class.java.simpleName
    private val DEBUG = true

    val userNameLiveData = MutableLiveData<String>().apply {
        postValue("東森寵物")
    }
    val userSubNameLiveData = MutableLiveData<String>().apply {
        postValue("集團員工")
    }

    override fun init(data: Any?) {
        val key = "init"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (DEBUG) logStar(TAG, "$key")
            }
            .subscribeOn(Schedulers.io())
            .add(key, TAG)
    }

    override fun resume() {

    }

    override fun pause() {
    }

    override fun destroy() {
    }
}