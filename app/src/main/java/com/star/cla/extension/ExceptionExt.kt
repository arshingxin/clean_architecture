package com.star.cla.extension

import android.util.Log
import com.star.cla.log.logStar
import com.star.cla.log.logStarError
import java.io.PrintWriter
import java.io.StringWriter

private val TAG = "ExceptionExt"
private val DEBUG = false

fun Exception.getExceptionMessage(callBackMsg: ((String) -> Unit?)? = null) {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    val errorMsg = sw.toString()
    callBackMsg?.invoke(errorMsg)
    logStarError(errorMsg)
    sw.close()
    pw.close()
}