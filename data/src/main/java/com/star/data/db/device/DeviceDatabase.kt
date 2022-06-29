package com.star.data.db.device

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DeviceCacheEntity::class], version = 1)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun deviceCacheDao(): DeviceCacheDao
}