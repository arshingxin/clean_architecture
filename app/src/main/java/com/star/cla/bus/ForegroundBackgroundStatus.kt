package com.star.cla.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay

object ForegroundBackgroundStatus {
    private var relay: Relay<Status> = BehaviorRelay.create<Status>().toSerialized()
    private var isBackground = true

    fun post(status: Status) {
        relay.accept(status)
    }

    fun relay(): Relay<Status> {
        return relay
    }

    fun setIsBackground(isBackground: Boolean) = run { ForegroundBackgroundStatus.isBackground = isBackground }

    fun isBackground() = isBackground

    sealed class Status {
        object Foreground: Status()
        object Background: Status()
    }
}