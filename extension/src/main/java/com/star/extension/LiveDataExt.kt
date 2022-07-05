package com.star.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this) {
        block(it)
    }
}

