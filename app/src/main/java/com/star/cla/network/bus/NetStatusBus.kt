package com.star.cla.network.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay

object NetStatusBus {
    private var relay: Relay<Status> = BehaviorRelay.create<Status>().toSerialized()
    // 是否連線
    private var isConnected = false

    fun post(status: Status) {
        relay.accept(status)
    }

    fun relay(): Relay<Status> {
        return relay
    }

    fun isConnected(connected: Boolean = true) {
        isConnected = connected
    }

    fun peek() = isConnected

    sealed class Status {
        object Connected: Status()
        object Disconnected: Status()
    }
}