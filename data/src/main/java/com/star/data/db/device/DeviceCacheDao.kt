package com.star.data.db.device

import androidx.room.*
import io.reactivex.rxjava3.core.Observable

@Dao
interface DeviceCacheDao {
    @Query("SELECT * FROM 'device_response'")
    fun getAll(): Observable<List<DeviceCacheEntity>>

    @Query("SELECT * FROM 'device_response' WHERE id = :id")
    fun get(id: Int = 1): Observable<List<DeviceCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deviceCacheEntity: DeviceCacheEntity)

    @Update
    fun update(deviceCacheEntity: DeviceCacheEntity)

    @Delete
    fun delete(deviceCacheEntity: DeviceCacheEntity)
}