package com.star.data.repository

import com.star.data.api.AppApi
import com.star.data.api.response.MemberCardInfo
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface IMemberCardRepo {
    fun getMemberCard(): Observable<MemberCardInfo>
    fun getEditUserBasicInfoKey(): String
}
class MemberCardRepo : KoinComponent, IMemberCardRepo {
    private val appApi: AppApi by inject(named(AppApi::class.java.simpleName))

    // TODO
    override fun getMemberCard(): Observable<MemberCardInfo> =
        Observable.just(MemberCardInfo())

    override fun getEditUserBasicInfoKey(): String = "user_basic_info_key"
}