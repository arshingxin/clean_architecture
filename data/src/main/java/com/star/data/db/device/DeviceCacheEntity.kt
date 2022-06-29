package com.star.data.db.device

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConst.DEVICE_RESPONSE_NAME)
data class DeviceCacheEntity(@PrimaryKey val id: Int = 1, val deviceResponse: String?)