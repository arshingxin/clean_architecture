package com.star.cla.di

import com.star.cla.ui.home.di.homeModule
import com.star.cla.ui.location.di.locationModule
import com.star.cla.ui.my.di.myModule
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
    locationModule,
    myModule
)