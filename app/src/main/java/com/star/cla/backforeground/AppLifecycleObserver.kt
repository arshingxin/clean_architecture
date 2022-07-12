package com.star.cla.backforeground

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.star.cla.MainApplication
import com.star.cla.bus.ForegroundBackgroundStatus
import com.star.cla.network.startNetworkMonitor
import com.star.cla.network.stopNetworkMonitor
import com.star.extension.log.logStar

class AppLifecycleObserver : LifecycleEventObserver {
    private val TAG = AppLifecycleObserver::class.java.name
    private val DEBUG = true

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (DEBUG) logStar(TAG, "onStateChanged event:$event")
        if (event == Lifecycle.Event.ON_STOP) {
            if (DEBUG) logStar(TAG, "ON_STOP")
            stopNetworkMonitor()
            ForegroundBackgroundStatus.setIsBackground(true)
            ForegroundBackgroundStatus.post(ForegroundBackgroundStatus.Status.Background)
        } else if (event == Lifecycle.Event.ON_START) {
            if (DEBUG) logStar(TAG, "ON_START")
            MainApplication.getApplicationContext()?.startNetworkMonitor()
            ForegroundBackgroundStatus.setIsBackground(false)
            ForegroundBackgroundStatus.post(ForegroundBackgroundStatus.Status.Foreground)
        }
    }
}