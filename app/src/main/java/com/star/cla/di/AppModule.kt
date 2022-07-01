package com.star.cla.di

import com.star.cla.ui.dashboard.di.dashboardModule
import com.star.cla.ui.home.di.homeModule
import com.star.data.di.*
import com.star.domain.di.useCaseModule

val appModule = listOf(
    networkModule,
    databaseModule,
    remoteRepoModule,
    preferencesModule,
    localCacheModule,
    useCaseModule,
    homeModule,
    dashboardModule
)