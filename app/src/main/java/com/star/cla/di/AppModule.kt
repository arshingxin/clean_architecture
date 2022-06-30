package com.star.cla.di

import com.star.cla.ui.dashboard.di.dashboardModule
import com.star.cla.ui.home.di.homeModule
import com.star.data.di.databaseModule
import com.star.data.di.localCacheModule
import com.star.data.di.networkModule
import com.star.data.di.preferencesModule

val appModule = listOf(
    networkModule,
    databaseModule,
    preferencesModule,
    localCacheModule,
    homeModule,
    dashboardModule
)