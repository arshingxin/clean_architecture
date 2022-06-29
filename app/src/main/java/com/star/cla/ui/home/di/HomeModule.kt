package com.star.cla.ui.home.di

import com.star.cla.ui.home.HomeViewModel
import com.star.data.api.AppApiV2
import com.star.data.repository.DeviceInfoDataRepo
import com.star.domain.repository.DeviceInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
//    single(named(IDeviceInfoDataRepo::class.java.simpleName)) {
//        DeviceInfoDataRepo(get(named(AppApi::class.java.simpleName)))
//    }
//
//    single(named(IDeviceInfoUseCase::class.java.simpleName)) {
//        DeviceInfoUseCase(get(named(IDeviceInfoDataRepo::class.java.simpleName)))
//    }

    viewModel {
        HomeViewModel(
            DeviceInfoUseCase(DeviceInfoDataRepo(get(named(AppApiV2::class.java.simpleName))))
        )
    }
}