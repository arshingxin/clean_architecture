package com.star.cla.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel

class MyViewModel : AutoDisposeViewModel() {

    private val _text = MutableLiveData<String>().apply {
        postValue("This is my Fragment")
    }
    val text: LiveData<String> = _text
    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}