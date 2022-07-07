package com.star.data.config

import android.util.Base64

object SettingConfig {
    object Header {
        const val HEADER_TOKEN = "689353e37fd3f91d215255b88fd33735059e6f0699279485676815e40df991ae"
        private val USER = "imojing@solmate.cc"
        private val PA = "solmateXimojing"
        val AUTHORIZATION = "Basic ${Base64.encodeToString("$USER:$PA".toByteArray(), Base64.NO_WRAP)}"
    }
}