package com.star.cla.ui.dashboard.di

import com.star.cla.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { DashboardViewModel(get()) }
}