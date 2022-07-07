package com.star.domain.usecase

import com.google.gson.Gson
import com.star.data.api.response.AdInfo
import com.star.data.repository.AdInfoDataRepo
import com.star.data.repository_cache.LocalAdInfoCache
import com.star.domain.model.AdInfoModel
import com.star.extension.fromJson
import com.star.extension.isJsonArray
import com.star.extension.log.log
import com.star.extension.log.logStar
import com.star.extension.report
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface IAdInfoUseCase {
    fun getRemoteAdInfoList(sn: String): Observable<List<AdInfoModel>>

    fun getLocalAdInfoList(): Observable<List<AdInfoModel>>
}

class AdInfoUseCase : KoinComponent, IAdInfoUseCase {
    private val TAG = DeviceInfoUseCase::class.java.simpleName
    private val DEBUG = false
    private val adInfoDataRepo: AdInfoDataRepo by inject()
    private val localAdInfoCache: LocalAdInfoCache by inject()

    override fun getRemoteAdInfoList(sn: String): Observable<List<AdInfoModel>> =
        adInfoDataRepo.getAdInfoList(sn)
            .map { localAdInfoCache.saveAdInfo(it.toJson()) }
            .onErrorReturn {
                it.log(TAG, "remote ad info list error: ${sn.ifEmpty { "設備SN為空的" }}")
                localAdInfoCache.saveAdInfo("")
                listOf<AdInfoModel>()
            }
            .concatMap { getLocalAdInfoList() }
            .subscribeOn(Schedulers.io())

    override fun getLocalAdInfoList(): Observable<List<AdInfoModel>> =
        localAdInfoCache.getAdInfo()
            .map {
                if (DEBUG) logStar(TAG, "getAdInfo:$it")
                var list = mutableListOf<AdInfo>()
                if (it.isJsonArray())
                    list = Gson().fromJson(it)
                if (DEBUG) logStar(TAG, "getAdInfo list:$list")
                list.toAdInfoModel()
            }
            .onErrorReturn {
                it.report(TAG)
                listOf()
            }
            .subscribeOn(Schedulers.io())
}

private fun List<AdInfo>.toAdInfoModel(): List<AdInfoModel> {
    val list = mutableListOf<AdInfoModel>()
    forEach {
        list.add(it.toAdInfoModel())
    }
    return list
}

private fun AdInfo.toAdInfoModel() = AdInfoModel(
    id, name, qrCodeLink, seconds, advertiserId, playType, material, materialMd5, banner, bannerMd5
)
