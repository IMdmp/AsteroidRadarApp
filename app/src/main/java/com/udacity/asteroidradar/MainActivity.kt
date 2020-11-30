package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.api.NasaApi
import retrofit2.Retrofit




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sample change
        val retrofit = AsteroidRadarApplication.NetworkHelper.retrofit
        val nasaApi = retrofit?.create(NasaApi::class.java)
        nasaApi?.getAsteroidData(getString(R.string.API_KEY))
    }
}
