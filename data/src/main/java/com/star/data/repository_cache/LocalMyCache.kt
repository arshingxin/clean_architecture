package com.star.data.repository_cache

import android.content.SharedPreferences
import com.star.data.customconst.PrefsConst
import com.star.extension.get
import com.star.extension.set
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

interface ILocalMyCache {
    fun saveMy(content: String)
    fun getMy(): Observable<String>
}

class LocalMyCache : KoinComponent, ILocalMyCache {
    private val appPreferences: SharedPreferences by inject(named(PrefsConst.App.NAME))

    override fun saveMy(content: String) {
        appPreferences[PrefsConst.App.MY_CONTENT] = content
    }

    override fun getMy(): Observable<String> =
        Observable.just(appPreferences[PrefsConst.App.MY_CONTENT, ""] ?: "")
}