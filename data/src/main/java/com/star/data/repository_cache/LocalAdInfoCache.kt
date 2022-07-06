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


interface ILocalAdInfoCache {
    fun saveAdInfo(content: String)
    fun getAdInfo(): Observable<String>
}

class LocalAdInfoCache : KoinComponent, ILocalAdInfoCache {
    private val appPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))
    private val deviceDatabase: DeviceDatabase by inject()

    override fun saveAdInfo(content: String) {
        appPreferences[PrefsConst.App.AD_INFO_CONTENT] = content
    }

    override fun getAdInfo(): Observable<String> =
        Observable.just(appPreferences[PrefsConst.App.AD_INFO_CONTENT, ""] ?: "")
}