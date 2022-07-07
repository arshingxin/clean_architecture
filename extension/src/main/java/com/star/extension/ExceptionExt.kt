package com.star.extension

import com.star.extension.log.logStarError
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
    logStarError(TAG, errorMsg)
    sw.close()
    pw.close()
}