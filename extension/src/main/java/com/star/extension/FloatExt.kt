package com.star.extension

fun Float.toNFloat(accuracy: Int): String {
    return if (accuracy > 0) String.format("%.${accuracy}f", this)
    else this.toInt().toString()
}