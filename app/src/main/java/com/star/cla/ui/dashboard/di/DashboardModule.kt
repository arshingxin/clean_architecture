package com.star.cla.ui.dashboard.di

import com.star.cla.ui.dashboard.DashboardViewModel
import com.star.data.repository.DeviceInfoDataRepo
import com.star.domain.repository.DeviceInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    single { DeviceInfoDataRepo(get()) }

    single { DeviceInfoUseCase(get(), get()) }

    viewModel { DashboardViewModel(get()) }
}