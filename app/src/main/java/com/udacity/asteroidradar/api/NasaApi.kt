package com.udacity.asteroidradar.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("")
    fun getAsteroidData(@Query("api_key") key:String): Call<ResponseBody>
}