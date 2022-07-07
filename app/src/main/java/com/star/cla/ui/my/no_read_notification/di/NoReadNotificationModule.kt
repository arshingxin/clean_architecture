package com.star.cla.ui.my.no_read_notification.di

import com.star.cla.ui.my.no_read_notification.NoReadNotificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noReadNotificationModule = module {
    viewModel { NoReadNotificationViewModel() }
}