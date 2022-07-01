package com.star.domain.di

import com.star.domain.usecase.DeviceInfoUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        DeviceInfoUseCase()
    }
}