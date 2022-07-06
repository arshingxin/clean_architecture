package com.star.data.di

import androidx.room.Room
import com.star.data.db.device.DatabaseConst
import com.star.data.db.device.DeviceDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    single(named(DatabaseConst.DB_NAME_DEVICE)) {
        Room.databaseBuilder(
            androidApplication(),
            DeviceDatabase::class.java,
            DatabaseConst.DB_NAME_DEVICE
        ).build()
    }
}