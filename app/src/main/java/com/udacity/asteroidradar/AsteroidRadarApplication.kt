package com.udacity.asteroidradar

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

class AsteroidRadarApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        initNetwork()
    }

    private fun initNetwork() {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.API_URL))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        NetworkHelper.retrofit = retrofit
    }

    object NetworkHelper {
        var retrofit: Retrofit? = null
    }
}