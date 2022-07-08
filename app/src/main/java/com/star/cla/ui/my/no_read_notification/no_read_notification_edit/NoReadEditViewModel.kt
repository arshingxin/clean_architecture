package com.star.cla.ui.my.no_read_notification.no_read_notification_edit

import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoReadEditViewModel: AutoDisposeViewModel() {
    private val TAG = NoReadEditViewModel::class.java.simpleName
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