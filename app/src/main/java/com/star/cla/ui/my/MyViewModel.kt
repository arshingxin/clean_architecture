package com.star.cla.ui.my

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel

class MyViewModel : AutoDisposeViewModel() {
    val userNameLiveData = MutableLiveData<String>().apply {
        postValue("東森寵物")
    }
    val userSubNameLiveData = MutableLiveData<String>().apply {
        postValue("集團員工")
    }
    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}