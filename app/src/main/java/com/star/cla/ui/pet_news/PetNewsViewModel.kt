package com.star.cla.ui.pet_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel

class PetNewsViewModel : AutoDisposeViewModel() {

    private val _text = MutableLiveData<String>().apply {
        postValue("寵物新聞")
    }
    val text: LiveData<String> = _text
    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }
}