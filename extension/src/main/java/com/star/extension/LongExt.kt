package com.star.extension

fun Long.addZeroFormat(): String {
    return if (10 - toInt() > 0) "0${toInt()}"
    else "${toInt()}"
}