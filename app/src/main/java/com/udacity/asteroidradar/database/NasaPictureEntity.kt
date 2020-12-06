package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pciture_of_the_day_table")
class NasaPictureEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
) {
//   constructor(copyright: String,date: String,explanation: String,hdurl: String,media_type: String,service_version: String,title: String,url: String) {
//       NasaPictureEntity(copyright,date,explanation,hdurl,media_type,service_version,title,url)
//   }
}