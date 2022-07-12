package com.star.data.di

import com.star.data.repository.AdInfoDataRepo
import com.star.data.repository.DeviceInfoDataRepo
import com.star.data.repository.MemberCardRepo
import org.koin.dsl.module

val remoteRepoModule = module {
    factory {
        DeviceInfoDataRepo()
    }
    factory {
        AdInfoDataRepo()
    }
    factory {
        MemberCardRepo()
    }
}