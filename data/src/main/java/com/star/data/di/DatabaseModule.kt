package com.star.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.star.data.db.device.DatabaseConst
import com.star.data.db.device.DeviceDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    single(named(DatabaseConst.DEVICE_RESPONSE_NAME)) {
        provideDeviceResponseDatabase(
            androidApplication()
        )
    }
}

private fun provideDeviceResponseDatabase(appContext: Application): RoomDatabase {
    return Room.databaseBuilder(
        appContext,
        DeviceDatabase::class.java,
        DatabaseConst.DEVICE_RESPONSE_NAME
    ).build()
}