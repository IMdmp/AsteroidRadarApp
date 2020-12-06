package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "daily_asteroid_table")
data class AsteroidEntity constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
){
    fun toDomain():Asteroid{
        return Asteroid(id,codename,closeApproachDate,absoluteMagnitude,estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
    }
}