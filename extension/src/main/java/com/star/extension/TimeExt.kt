package com.star.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTimeString(format: String = "yyyy/MM/dd HH:mm:ss", locale: Locale = Locale.TAIWAN): String = SimpleDateFormat(format, locale).format(this)

fun Int.toTimeString(format: String = "yyyy/MM/dd HH:mm:ss", locale: Locale = Locale.TAIWAN): String = SimpleDateFormat(format, locale).format(this * 1000)

fun Int.toOverXDaysTimestamp(): Long = (this.toLong() * 60 * 60 * 24 * 1000)

