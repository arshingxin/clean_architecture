package com.star.data.db.device

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConst.DB_NAME_DEVICE)
data class DeviceCacheEntity(@PrimaryKey val id: Int = 1, val deviceResponse: String?)