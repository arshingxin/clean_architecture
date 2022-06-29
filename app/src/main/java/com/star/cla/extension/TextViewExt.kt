package com.star.cla.extension

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setTextSizePx(textSize: Int) {
    setTextSizePx(textSize.toFloat())
}

fun TextView.setTextSizePx(textSize: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
}

fun TextView.setTextColor(context: Context, resColor: Int) {
    this.setTextColor(ContextCompat.getColor(context, resColor))
}