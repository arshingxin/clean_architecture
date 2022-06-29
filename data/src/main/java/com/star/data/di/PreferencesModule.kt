package com.star.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.star.data.customconst.PrefsConst
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val preferencesModule = module {
    single(named(PrefsConst.App.NAME)) { provideAppPreferences(androidApplication()) }
}

private fun provideAppPreferences(appContext: Application): SharedPreferences =
        appContext.getSharedPreferences(PrefsConst.App.NAME, Context.MODE_PRIVATE)
