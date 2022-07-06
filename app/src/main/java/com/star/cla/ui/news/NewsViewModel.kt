package com.star.cla.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel

class NewsViewModel : AutoDisposeViewModel() {

    private val _text = MutableLiveData<String>().apply {
        postValue("This is notifications Fragment")
    }
    val text: LiveData<String> = _text
    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}