package com.star.cla.extension

import com.star.cla.log.log
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.report(tag: String): String {
    val sw = StringWriter()
    printStackTrace(PrintWriter(sw))
    log(tag)
    return sw.toString()
}

fun throwException(tag: String, msg: String): Nothing = throw Exception("[$tag]$msg")

