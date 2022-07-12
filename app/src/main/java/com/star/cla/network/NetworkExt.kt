package com.star.cla.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.star.cla.network.bus.NetStatusBus
import com.star.extension.log.logStar


private val TAG = "NetworkExt"
private val DEBUG = true

private var netRequest: NetworkRequest? = null
private var connectivityManager: ConnectivityManager? = null
private var networkCallback = object : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        if (DEBUG) logStar(TAG, "networkCallback 連線成功")
        NetStatusBus.isConnected()
        NetStatusBus.post(NetStatusBus.Status.Connected)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        if (DEBUG) logStar(TAG, "networkCallback 連線失敗")
        NetStatusBus.isConnected(false)
        NetStatusBus.post(NetStatusBus.Status.Disconnected)
    }
}

fun Context.startNetworkMonitor() {
    if (DEBUG) logStar(TAG, "startNetworkMonitor")
    netRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    netRequest?.let { request ->
        connectivityManager?.registerNetworkCallback(request, networkCallback)
    }
}

fun stopNetworkMonitor() {
    if (DEBUG) logStar(TAG, "stopNetworkMonitor")
    connectivityManager?.unregisterNetworkCallback(networkCallback)
    connectivityManager = null
    netRequest = null
}