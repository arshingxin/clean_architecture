package com.star.extension

import android.content.res.Resources
import android.view.KeyEvent
import java.text.DecimalFormat

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

// 每三位數用,隔開
fun Int?.toPriceFormat(): String {
    return if (this == null) 0.toString()
    else DecimalFormat("#,###").format(this)
}

fun Int.toFormatTime() = String.format("%02d", this)

fun Int.toKeyText() = when(this) {
    KeyEvent.KEYCODE_VOLUME_DOWN -> "音量小"
    KeyEvent.KEYCODE_VOLUME_UP -> "音量大"
    KeyEvent.KEYCODE_ENTER -> "OK"
    KeyEvent.KEYCODE_F7 -> "關閉"
    KeyEvent.KEYCODE_F8 -> "選單"
    KeyEvent.KEYCODE_DPAD_UP -> "上"
    KeyEvent.KEYCODE_DPAD_DOWN -> "下"
    KeyEvent.KEYCODE_DPAD_LEFT -> "左"
    KeyEvent.KEYCODE_DPAD_RIGHT -> "右"
    KeyEvent.KEYCODE_BACK -> "返回"
    else -> ""
}

fun Int.toDelayTime(): Int {
    return when {
        this <= 0 -> randomInt(end = 30)
        this > 99999999 -> this / 10000000
        this > 9999999 -> this / 1000000
        this > 999999 -> this / 100000
        this > 99999 -> this / 10000
        this > 9999 -> this / 1000
        this > 999 -> this / 100
        this > 99 -> this / 10
        else -> this
    }
}