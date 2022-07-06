package com.star.data.di

import android.graphics.Color
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@JsonQualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class HexColor

class ColorAdapter {
    @FromJson
    @HexColor
    fun fromJson(rgb: String): Int {
        return try {
            var color = Integer.parseInt(rgb.substring(1), 16)
            if (rgb.length == 7) {
                color = color or -0x1000000
            } else if (rgb.length != 9) {
                Color.WHITE
            }
            color
        } catch (e: NumberFormatException) {
            Color.WHITE
        }
    }

    @ToJson
    fun toJson(@HexColor rgb: Int): String {
        return String.format("#%06x", rgb)
    }
}