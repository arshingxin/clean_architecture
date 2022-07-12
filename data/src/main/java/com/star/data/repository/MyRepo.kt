package com.star.data.repository

import com.star.data.api.AppApi
import com.star.data.api.response.MyItemInfo
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface IMyRepo {
    fun getMy(): Observable<MutableList<MyItemInfo>>
}
class MyRepo : KoinComponent, IMyRepo {
    private val appApi: AppApi by inject(named(AppApi::class.java.simpleName))

    // TODO
    override fun getMy(): Observable<MutableList<MyItemInfo>> =
        Observable.just(mutableListOf())
}