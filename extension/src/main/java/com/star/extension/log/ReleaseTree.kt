package com.star.extension.log

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }
        // TODO can log to server
//        Crashlytics.log(priority, tag, message)
//
//        if (t != null) {
//            Crashlytics.logException(t)
//        }

        super.log(priority, tag, message, t)
    }

    override fun formatMessage(message: String, args: Array<out Any?>): String {
        return super.formatMessage(message.replace("%", "%%"), args)
    }
}