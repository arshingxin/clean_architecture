package com.star.cla.ui.home.di

import com.star.cla.ui.home.HomeViewModel
import com.star.data.api.AppApiV2
import com.star.data.repository.DeviceInfoDataRepo
import com.star.data.repository.IDeviceInfoDataRepo
import com.star.domain.interactor.IDeviceInfoUseCase
import com.star.domain.repository.DeviceInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    single<IDeviceInfoDataRepo> { DeviceInfoDataRepo(get(named(AppApiV2::class.java.simpleName))) }

    single<IDeviceInfoUseCase> { DeviceInfoUseCase(get()) }

    viewModel { HomeViewModel(get()) }
}