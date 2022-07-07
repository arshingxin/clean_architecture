package com.star.data.di

import android.content.SharedPreferences
import android.os.Build
import com.star.data.config.SettingConfig.Header.AUTHORIZATION
import com.star.data.config.SettingConfig.Header.HEADER_TOKEN
import com.star.data.customconst.PrefsConst.App.DEVICE_ID
import com.star.extension.get
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import java.util.*

interface IInterceptorModule {
    val logInterceptor: Interceptor
    val headerInterceptor: Interceptor
    val redirectInterceptor: Interceptor
}

private const val DEBUGGABLE = "app.debug"
class InterceptorModule(
    private val appSharedPreferences: SharedPreferences
) : IInterceptorModule {
    override val logInterceptor: Interceptor get() {
        val debuggable: Boolean = appSharedPreferences[DEBUGGABLE, false] ?: false
        return HttpLoggingInterceptor().apply {
            level = if (debuggable || BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
                // for debug complete raw response data
                // logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    override val headerInterceptor: Interceptor get() {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val locale = Locale.getDefault()
            val deviceId = appSharedPreferences[DEVICE_ID, ""] ?: ""
            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", AUTHORIZATION)
                .header("token", HEADER_TOKEN)
                .header("PLATFORM", "Android")
                .header("DEVICE-ID", deviceId)
                .header("USER-BRAND", Build.BRAND)
                .header("USER-MODELS", Build.MODEL)
                .header("USER-OS-VER", Build.VERSION.RELEASE)
                .header("LOCALE", locale.country)
                .header("CHANNEL", BuildConfig.BUILD_TYPE)
                .header("Connection", "close")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    object ErrorConst {
        object Code {
            const val RedirectOne = 301
            const val RedirectTwo = 302
            const val RedirectThree = 307
        }
    }
    override val redirectInterceptor: Interceptor get() {
        return Interceptor { chain ->
            var request = chain.request()
            var response: Response = chain.proceed(request)
            val responseCode = response.code
            if (responseCode == ErrorConst.Code.RedirectOne ||
                responseCode == ErrorConst.Code.RedirectTwo ||
                responseCode == ErrorConst.Code.RedirectThree) {
                val location = response.header("Location")
                location.apply {
                    request = request.newBuilder()
                        .url(this.toString())
                        .build()
                }
            }
            chain.proceed(request)
        }
    }
}