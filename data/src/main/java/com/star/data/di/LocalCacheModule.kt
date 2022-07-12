package com.star.data.di

import com.star.data.repository_cache.LocalAdInfoCache
import com.star.data.repository_cache.LocalDeviceCache
import com.star.data.repository_cache.LocalMemberCardCache
import com.star.data.repository_cache.LocalMyCache
import org.koin.dsl.module

val localCacheModule = module {
    factory {
        LocalDeviceCache()
    }
    factory {
        LocalAdInfoCache()
    }
    factory {
        LocalMemberCardCache()
    }
    factory {
        LocalMyCache()
    }
}