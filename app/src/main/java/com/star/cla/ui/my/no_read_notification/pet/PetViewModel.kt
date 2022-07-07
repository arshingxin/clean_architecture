package com.star.cla.ui.my.no_read_notification.pet

import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PetViewModel: AutoDisposeViewModel() {
    private val TAG = PetViewModel::class.java.simpleName
    private val DEBUG = true

    override fun resume() {
        val key = "resume"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (DEBUG) logStar(TAG, "$key")
            }
            .subscribeOn(Schedulers.io())
            .add(key, TAG)
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}