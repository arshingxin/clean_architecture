package com.star.extension.log

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class TimberLogger {
    fun setup(debugMode: Boolean) {
        if (debugMode) {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("Timber")
                .showThreadInfo(true)
                .methodCount(3)
                .methodOffset(5)
                .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}