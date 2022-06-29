package com.star.data.extension

import java.text.SimpleDateFormat

val ERROR_TIME_HINT = 556654987L

fun String.toTimestamp(format: String = "yyyy/MM/dd HH:mm"): Long {
    if (this.contains("null") || this.isNullOrEmpty()) return ERROR_TIME_HINT
    val formatter = SimpleDateFormat(format)
    val date = formatter.parse(this)
    return date.time // return millisecond
}