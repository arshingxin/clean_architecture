package com.star.domain.log

import android.util.Log

fun logStar(tag: String, msg: String?) {
    Log.d("[$tag][star debug]", "$msg")
}

fun logStarError(tag: String, msg: String?) {
    logStar(tag, msg.toString())
    Log.e("[$tag][star debug error]", msg.toString())
}

fun log(tag: String, msg: String) {
    Log.d("[$tag]",msg)
}

fun logError(tag: String, msg: String) {
    logStar(tag, msg)
    Log.e("[$tag]", msg)
}

fun logException(tag: String, msg: String) {
    logError(tag, msg)
}

fun Throwable.log(tag: String) {
    logException(tag, message.toString())
}