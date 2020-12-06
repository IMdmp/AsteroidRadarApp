package com.udacity.asteroidradar.database


data class NetworkNasaPicture(
    val copyright: String?,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String?,
    val title: String,
    val url: String
) {
    fun asEntity(): NasaPictureEntity {
        return NasaPictureEntity(
            copyright = copyright?:"",
            date = date,
            explanation = explanation,
            hdurl = hdurl,
            media_type = media_type,
            service_version = service_version?:"",
            title = title,
            url = url
        )
    }
}