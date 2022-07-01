package com.star.data.di

import com.star.data.repository.DeviceInfoDataRepo
import org.koin.dsl.module

val remoteRepoModule = module {
    factory {
        DeviceInfoDataRepo()
    }
}