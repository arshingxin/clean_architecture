package com.star.cla.log

import android.os.Build
import android.util.Log
import com.orhanobut.logger.Logger
import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newPriority = if (Build.MANUFACTURER == "HUAWEI" || Build.MANUFACTURER == "Meitu") {
            when (priority) {
                Log.VERBOSE, Log.DEBUG, Log.INFO -> Log.ERROR
                else -> priority
            }
        } else {
            priority
        }
        // TODO can log to server
//        if (priority >= Log.INFO) {
//            Crashlytics.log(message)
//        }
        Logger.log(newPriority, "", message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element)
    }

    override fun formatMessage(message: String, args: Array<out Any?>): String {
        return super.formatMessage(message.replace("%", "%%"), args)
    }
}