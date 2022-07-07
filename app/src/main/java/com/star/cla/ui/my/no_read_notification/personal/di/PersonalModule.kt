package com.star.cla.ui.my.no_read_notification.personal.di

import com.star.cla.ui.my.no_read_notification.personal.PersonalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val personalModule = module {
    viewModel { PersonalViewModel() }
}