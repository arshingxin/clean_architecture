package com.star.cla.extension

import com.star.cla.log.logError
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.report(tag: String): String {
    val sw = StringWriter()
    printStackTrace(PrintWriter(sw))
    logError(tag, "Throwable report:: $sw")
    return sw.toString()
}

