package com.star.data.api

import com.star.data.api.response.DeviceInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {
    @GET("devices/{mac}")
    fun getDeviceInfo(@Path("mac") mac: String): Observable<DeviceInfo>
}