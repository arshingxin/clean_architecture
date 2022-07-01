package com.star.cla.ui.home.di

import com.star.cla.ui.home.HomeViewModel
import com.star.data.api.AppApiV2
import com.star.data.cache.LocalDeviceCache
import com.star.data.customconst.PrefsConst
import com.star.data.db.device.DatabaseConst
import com.star.data.repository.DeviceInfoDataRepo
import com.star.data.repository.IDeviceInfoDataRepo
import com.star.domain.usecase.DeviceInfoUseCase
import com.star.domain.usecase.IDeviceInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    single<IDeviceInfoDataRepo> {
        DeviceInfoDataRepo(get(named(AppApiV2::class.java.simpleName)))
    }

    single<IDeviceInfoUseCase> {
        DeviceInfoUseCase(
            get(),
            LocalDeviceCache(
                get(named(PrefsConst.App.NAME)),
                get(named(DatabaseConst.DB_NAME_DEVICE))
            )
        )
    }

    viewModel { HomeViewModel(get()) }
}