package com.star.data.config

import android.util.Base64

object SettingConfig {
    object Header {
        const val HEADER_TOKEN = ""
        private val USER = ""
        private val PA = ""
        val AUTHORIZATION = "Basic ${Base64.encodeToString("$USER:$PA".toByteArray(), Base64.NO_WRAP)}"
    }
}