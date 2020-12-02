package com.udacity.asteroidradar.api


import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("neo/rest/v1/feed")
    fun getAsteroidData(@Query("start_date") startDate:String,
                        @Query("end_date") endDate:String,
                        @Query("api_key") key:String): Call<String>


    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") key:String): Call<String>
}