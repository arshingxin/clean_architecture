package com.star.cla.log

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.star.data.BuildConfig
import timber.log.Timber

class TimberLogger {
    fun setup(debugMode: Boolean = BuildConfig.DEBUG) {
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