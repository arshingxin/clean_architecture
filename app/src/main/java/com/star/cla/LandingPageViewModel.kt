package com.star.cla

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.util.concurrent.TimeUnit

class LandingPageViewModel: AutoDisposeViewModel() {
    private val TAG = LandingPageViewModel::class.java.simpleName
    private val DEBUG = true
    private var _switchToMainActivity = MutableLiveData<Boolean>()
    val switchToMainActivity: LiveData<Boolean> = _switchToMainActivity

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
            .timer(if (BuildConfig.DEBUG) 0 else 3L, TimeUnit.SECONDS)
            .map { _switchToMainActivity.postValue(true) }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}