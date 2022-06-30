package com.star.data.di

import com.star.data.cache.ILocalDeviceCache
import com.star.data.cache.LocalDeviceCache
import com.star.data.customconst.PrefsConst
import com.star.data.db.device.DatabaseConst
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localCacheModule = module {
    single(named(ILocalDeviceCache::class.java.simpleName)) {
        LocalDeviceCache(
            get(named(PrefsConst.App.NAME)),
            get(named(DatabaseConst.DB_NAME_DEVICE))
        )
    }
}