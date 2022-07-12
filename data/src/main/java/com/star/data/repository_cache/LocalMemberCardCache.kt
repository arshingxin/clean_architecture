package com.star.data.repository_cache

import android.content.SharedPreferences
import com.star.data.customconst.PrefsConst
import com.star.data.db.device.DeviceDatabase
import com.star.extension.get
import com.star.extension.set
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface ILocalMemberCardCache {
    fun saveMemberCard(content: String)
    fun getMemberCard(): Observable<String>
}

class LocalMemberCardCache : KoinComponent, ILocalMemberCardCache {
    private val appPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))
    private val deviceDatabase: DeviceDatabase by inject()

    override fun saveMemberCard(content: String) {
        appPreferences[PrefsConst.App.MEMBER_CARD_CONTENT] = content
    }

    override fun getMemberCard(): Observable<String> =
        Observable.just(appPreferences[PrefsConst.App.MEMBER_CARD_CONTENT, ""] ?: "")
}