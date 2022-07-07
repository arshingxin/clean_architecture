package com.star.data.db.device

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DeviceCacheEntity::class], version = 1, exportSchema = false)
abstract class DeviceDatabase : RoomDatabase() {
    abstract val deviceCacheDao: DeviceCacheDao
}