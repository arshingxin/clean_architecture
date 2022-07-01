package com.star.extension.config

import android.content.SharedPreferences

object ExtensionConfig {
    object Path {
        var fileExternalPath = ""
    }
    var appSharedPreferences: SharedPreferences? = null
}