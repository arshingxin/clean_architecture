package com.star.cla.di

import com.star.cla.ui.home.di.homeModule
import com.star.cla.ui.my.di.myModule
import com.star.cla.ui.my.news_collection.di.newsCollectionModule
import com.star.cla.ui.my.news_collection.news_edit.di.newsEditModule
import com.star.cla.ui.my.no_read_notification.di.noReadNotificationModule
import com.star.cla.ui.my.no_read_notification.discount.di.discountModule
import com.star.cla.ui.my.no_read_notification.no_read_notification_edit.di.noReadEditModule
import com.star.cla.ui.my.no_read_notification.personal.di.personalModule
import com.star.cla.ui.my.no_read_notification.pet.di.petModule
import com.star.cla.ui.my.user_info.di.userInfoModule
import com.star.cla.ui.my.user_info.user_info_edit.di.userInfoEditModule
import com.star.cla.ui.store_location.di.storeLocationModule
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
    storeLocationModule,
    myModule,
    noReadNotificationModule,
    discountModule,
    personalModule,
    petModule,
    newsCollectionModule,
    newsEditModule,
    noReadEditModule,
    userInfoModule,
    userInfoEditModule
)