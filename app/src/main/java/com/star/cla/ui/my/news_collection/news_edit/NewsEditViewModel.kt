package com.star.cla.ui.my.news_collection.news_edit

import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsEditViewModel: AutoDisposeViewModel() {
    private val TAG = NewsEditViewModel::class.java.simpleName
    private val DEBUG = true

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