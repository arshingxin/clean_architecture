package com.star.domain.usecase

import com.google.gson.Gson
import com.star.data.api.response.MyItemInfo
import com.star.data.repository.MyRepo
import com.star.data.repository_cache.LocalMyCache
import com.star.domain.model.MyItemInfoModel
import com.star.domain.model.toMyItemInfoModel
import com.star.extension.fromJson
import com.star.extension.log.log
import com.star.extension.report
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface IMyUseCase {
    fun getRemoteMy(): Observable<MutableList<MyItemInfoModel>>
    fun getLocalMy(): Observable<MutableList<MyItemInfoModel>>
}

class MyUseCase : KoinComponent, IMyUseCase {
    private val TAG = MyUseCase::class.java.simpleName
    private val DEBUG = false
    private val myRepo: MyRepo by inject()
    private val localMyCache: LocalMyCache by inject()

    override fun getRemoteMy(): Observable<MutableList<MyItemInfoModel>> =
        myRepo.getMy()
            .map { localMyCache.saveMy(it.toJson()) }
            .onErrorReturn {
                it.log(TAG, "remote my error")
                localMyCache.saveMy("")
                mutableListOf<MyItemInfoModel>()
            }
            .concatMap { getLocalMy() }
            .subscribeOn(Schedulers.io())

    override fun getLocalMy(): Observable<MutableList<MyItemInfoModel>> =
        localMyCache.getMy()
            .map {
                val json = "[\n" +
                        "  {\n" +
                        "    \"title\": \"會員服務\",\n" +
                        "    \"items\": [\n" +
                        "      \"點數/交易紀錄\",\n" +
                        "      \"常見問題\",\n" +
                        "      \"隱私政策\",\n" +
                        "      \"服務條款\",\n" +
                        "      \"客服信箱\"\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]"
                val myItemInfoList: MutableList<MyItemInfo> = Gson().fromJson(json)
                val list = mutableListOf<MyItemInfoModel>()
                myItemInfoList.forEach {
                    list.add(it.toMyItemInfoModel())
                }
                list
            }
            .onErrorReturn {
                it.report(TAG)
                mutableListOf()
            }
            .subscribeOn(Schedulers.io())
}