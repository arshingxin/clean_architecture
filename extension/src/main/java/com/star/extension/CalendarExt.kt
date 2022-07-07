package com.star.extension

import android.icu.text.SimpleDateFormat
import java.util.*

fun getYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

fun getMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

/**
 * <10 會補 0
 */
fun getDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun getDate(separate: String = "-"): String =
    String.format("%d$separate%02d$separate%02d", getYear(), getMonth(), getDay())

/**
 * server index is 0-6, start from sunday, monday....
 */
fun getDayOfWeek(): Int {
    val calendar = Calendar.getInstance()
    // return 1-7 start from sunday, monday....
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    // ex: 星期四, dayOfWeek = 5
    // ex: 星期日, dayOfWeek = 1
    return dayOfWeek - 1
}

fun getDayOfWeekText(timestamp: Long): String =
    SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp)

/**
 * 0-23
 */
fun getHourOfDay(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.HOUR_OF_DAY)
}

fun getDayOfMonth(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.DAY_OF_MONTH)
}

/**
 * 0-59
 */
fun getMinuteOfHour(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.MINUTE)
}

// 取得n天候日期, 1:表示明天
fun getNDay(nDay: Int = 1): Long {
    val gc = GregorianCalendar()
    gc.add(Calendar.DATE, nDay)
    return gc.timeInMillis
}