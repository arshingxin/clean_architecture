package com.star.cla.log

import android.util.Log
import com.star.cla.MainApplication
import com.star.cla.extension.toTimeString

fun logStar(msg: String?) {
    Log.d("star debug", msg.toString())
}

fun logStarError(msg: String?) {
    logStar(msg.toString())
    logError("star debug error", msg.toString())
}

fun logError(tag: String, msg: String) {
    logStar(msg)
    Log.e(tag, msg)
}


private const val LOG_DATE_FORMAT = "yyyyMMdd"
//private var LOG_FOLDER_TODAY =
//    "${MainApplication.ge}/${System.currentTimeMillis().toTimeString(LOG_DATE_FORMAT)}/"
fun writeErrorLog(tag: String, msg: String?) {

}
