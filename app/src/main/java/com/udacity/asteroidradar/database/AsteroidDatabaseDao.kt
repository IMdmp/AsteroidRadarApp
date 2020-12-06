package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.FtsOptions.Order
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AsteroidDatabaseDao {

    @Insert
    suspend fun insert(asteroidEntity:AsteroidEntity)

    @Insert
    suspend fun insert(nasaPictureEntity: NasaPictureEntity)

    @Query("SELECT * FROM daily_asteroid_table ORDER BY id DESC LIMIT 1")
    suspend fun getAsteroid():AsteroidEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM daily_asteroid_table ORDER BY id")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>
}