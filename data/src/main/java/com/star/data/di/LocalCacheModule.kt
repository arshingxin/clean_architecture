package com.star.data.di

import com.star.data.cache.LocalDeviceCache
import org.koin.dsl.module

val localCacheModule = module {
    factory {
        LocalDeviceCache()
    }
}