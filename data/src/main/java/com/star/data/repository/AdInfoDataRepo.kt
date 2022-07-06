package com.star.data.repository

import com.star.data.api.AppApi
import com.star.data.api.response.AdInfo
import com.star.extension.log.log
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

private val TAG = "AdInfoDataRepo"

interface IAdInfoDataRepo {
    fun getAdInfoList(sn: String): Observable<List<AdInfo>>
}

class AdInfoDataRepo : KoinComponent, IAdInfoDataRepo {
    private val appApi: AppApi by inject(named(AppApi::class.java.simpleName))

    override fun getAdInfoList(sn: String): Observable<List<AdInfo>> =
        appApi.getAds(sn)
            .onErrorReturn {
                it.log(TAG, "remote ad info list error: ${sn.ifEmpty { "設備SN為空的" }}")
                listOf()
            }

}