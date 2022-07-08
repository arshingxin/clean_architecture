package com.star.cla

import androidx.lifecycle.ViewModel
import com.star.cla.network.NetworkStatus
import com.star.cla.network.bus.NetStatusBus
import com.star.extension.addTo
import com.star.extension.removeFrom
import com.star.extension.report
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.component.KoinComponent

abstract class AutoDisposeViewModel: KoinComponent, NetworkStatus, ViewModel() {
    private val TAG = AutoDisposeViewModel::class.java.simpleName
    open val compositeDisposable = CompositeDisposable()
    open val downloadCompositeDisposable = CompositeDisposable()
    val disposableMap = linkedMapOf<String, Disposable>()

    init {
        NetStatusBus
            .relay()
            .map {
                when (it) {
                    is NetStatusBus.Status.Connected -> networkConnected()
                    else -> { networkDisconnected() }
                }
            }
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .subscribe({}, { it.report(TAG) })
    }

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

    override fun networkConnected() { }
    override fun networkDisconnected() { }
    abstract fun resume()
    abstract fun pause()
    abstract fun destroy()
}