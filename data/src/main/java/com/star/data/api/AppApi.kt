package com.star.data.api

import com.star.data.api.response.AdInfo
import com.star.data.api.response.MemberCardInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {
    @GET("devices/{mac}/ads")
    fun getAds(@Path("mac") mac: String): Observable<List<AdInfo>>

    // TODO
    fun getMemberCard(): Observable<MemberCardInfo>
}