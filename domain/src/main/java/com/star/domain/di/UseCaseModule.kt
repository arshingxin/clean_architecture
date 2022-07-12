package com.star.domain.di

import com.star.domain.usecase.AdInfoUseCase
import com.star.domain.usecase.DeviceInfoUseCase
import com.star.domain.usecase.MemberCardUseCase
import com.star.domain.usecase.MyUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        DeviceInfoUseCase()
    }
    factory {
        AdInfoUseCase()
    }
    factory {
        MemberCardUseCase()
    }
    factory {
        MyUseCase()
    }
}