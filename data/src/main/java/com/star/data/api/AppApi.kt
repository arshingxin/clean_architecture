package com.star.data.api

import com.star.data.api.response.AdInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {
    @GET("devices/{mac}/ads")
    fun getAds(@Path("mac") mac: String): Observable<List<AdInfo>>
}