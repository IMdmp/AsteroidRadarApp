package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.network.NasaApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object Network {
    private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

    val nasaApi =retrofit.create(NasaApi::class.java)
}