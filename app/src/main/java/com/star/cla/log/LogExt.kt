package com.star.cla.log

import timber.log.Timber

fun logStar(msg: String?) {
    log("star debug", msg.toString())
}

fun logStarError(msg: String?) {
    logStar(msg.toString())
    logError("star debug error", msg.toString())
}

fun log(tag: String, msg: String) {
    Timber.d("[$tag]$msg")
}

fun logError(tag: String, msg: String) {
    logStar(msg)
    Timber.e("[$tag]$msg")
}
