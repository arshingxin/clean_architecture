package com.star.cla

import androidx.lifecycle.ViewModel
import com.star.cla.extension.addTo
import com.star.cla.extension.removeFrom
import com.star.cla.extension.report
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class AutoDisposeViewModel: ViewModel() {
    private val TAG = AutoDisposeViewModel::class.java.simpleName
    open val compositeDisposable = CompositeDisposable()
    open val downloadCompositeDisposable = CompositeDisposable()
    val disposableMap = linkedMapOf<String, Disposable>()

    override fun onCleared() {
        disposableMap()
        downloadCompositeDisposable.dispose()
        compositeDisposable.dispose()
    }

    private fun disposableMap() {
        disposableMap.values.forEach {
            it.removeFrom(compositeDisposable)
            it.removeFrom(downloadCompositeDisposable)
        }
    }

    fun getCurrentTimeSec() = getCurrentTime() / 1000

    fun getCurrentTime() = System.currentTimeMillis()

    fun <T : Any> Observable<T>.add(key: String, tag: String = TAG, exceptionAction: (() -> Unit?)? = null): Disposable {
        disposableMap[key].removeFrom(compositeDisposable)
        return this.subscribe({}, {
            exceptionAction?.invoke()
            it.report(tag) }
        ).addTo(compositeDisposable)
    }

    abstract fun resume()
    abstract fun pause()
    abstract fun destroy()
}