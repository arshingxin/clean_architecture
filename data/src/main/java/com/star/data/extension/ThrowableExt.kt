package com.star.data.extension

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.report(tag: String): String {
    val sw = StringWriter()
    printStackTrace(PrintWriter(sw))
    Log.e(tag, "Throwable report:: $sw")
    return sw.toString()
}