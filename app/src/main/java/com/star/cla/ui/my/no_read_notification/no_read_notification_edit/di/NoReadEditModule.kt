package com.star.cla.ui.my.no_read_notification.no_read_notification_edit.di

import com.star.cla.ui.my.no_read_notification.no_read_notification_edit.NoReadEditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noReadEditModule = module {
    viewModel { NoReadEditViewModel() }
}