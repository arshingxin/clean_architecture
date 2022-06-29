package com.star.data.api

import com.star.data.api.response.DeviceInfo
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApiV2 {
    @GET("devices/{mac}")
    fun getDeviceInfo(@Path("mac") mac: String): Observable<DeviceInfo>

    @GET("devices/{mac}")
    fun getDeviceInfoTmp(@Path("mac") mac: String): Observable<ResponseBody>
}