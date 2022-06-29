package com.star.cla.extension

fun Long.addZeroFormat(): String {
    return if (10 - toInt() > 0) "0${toInt()}"
    else "${toInt()}"
}