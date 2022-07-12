package com.star.cla.ui.pet_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PetNewsViewModel : AutoDisposeViewModel() {
    private val TAG = PetNewsViewModel::class.java.simpleName
    private val DEBUG = true
    private val _text = MutableLiveData<String>().apply {
        postValue("寵物新聞")
    }
    val text: LiveData<String> = _text

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
