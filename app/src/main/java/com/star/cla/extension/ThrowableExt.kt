package com.star.cla.extension

import com.star.cla.log.log
import com.star.cla.log.logException
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.report(tag: String): String {
    val sw = StringWriter()
    printStackTrace(PrintWriter(sw))
    log(tag)
    return sw.toString()
}

