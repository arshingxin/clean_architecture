package com.star.cla.network

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.star.cla.network.bus.NetStatusBus
import com.star.extension.log.logStar
import com.star.extension.repeatWhenCustom
import com.star.extension.report
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers.io
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.TimeUnit

private val TAG = "NetworkExt"
private val DEBUG = false
private const val DETECT_PERIOD_TIME = 5000L

fun getResponse(url: String): Observable<Response> {
    val client = OkHttpClient()
    val request: Request = Request.Builder().url(url).build()
    return Observable.create<Response?> { emitter: ObservableEmitter<Response?> ->
        try {
            val response: Response = client.newCall(request).execute()
            emitter.onNext(response)
        } catch (exception: IOException) {
            emitter.onError(exception)
        } finally {
            emitter.onComplete()
        }
    }
}


sealed class DetectNetworkStatus {
    object Connected : DetectNetworkStatus()
    object Disconnected : DetectNetworkStatus()
}

fun Context.detectNetwork() {
    ReactiveNetwork
        .observeNetworkConnectivity(this)
        .flatMap { connectivity ->
            if (connectivity.state() == NetworkInfo.State.CONNECTED)
                getResponse("https://www.google.com/")
            else
                Observable.error(RuntimeException("not connected"))
        }
        .subscribeOn(io())
        .subscribe({
            if (DEBUG) logStar(TAG, "連線成功")
            NetStatusBus.isConnected()
            NetStatusBus.post(NetStatusBus.Status.Connected)
        }, {
            if (DEBUG) logStar(TAG, "連線失敗")
            NetStatusBus.isConnected(false)
            NetStatusBus.post(NetStatusBus.Status.Disconnected)
            it.report(TAG)
        })

    io.reactivex.rxjava3.core.Observable
        .just(true)
        .map {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress("8.8.8.8", 53), DETECT_PERIOD_TIME.toInt())
                socket.close()
                if (DEBUG) logStar(TAG, "連線成功")
                DetectNetworkStatus.Connected
            } catch (e: Exception) {
                e.report(TAG)
                if (DEBUG) logStar(TAG, "連線失敗")
                DetectNetworkStatus.Disconnected
            }
        }
        .map {
            when (it) {
                is DetectNetworkStatus.Connected -> NetStatusBus.isConnected()
                is DetectNetworkStatus.Disconnected -> NetStatusBus.isConnected(false)
            }
        }
        .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
        .repeatWhenCustom(DETECT_PERIOD_TIME, TimeUnit.MILLISECONDS)
        .subscribe({}, { it.report(TAG) })
}