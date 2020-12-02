package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.await
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sample change
        val retrofit = AsteroidRadarApplication.NetworkHelper.retrofit
        val nasaApi = retrofit?.create(NasaApi::class.java)

        val df = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        val startFormattedDate: String = df.format(Date())

        val database = AsteroidDatabase.getDatabase(this)
        val endFormattedDate :String = df.format(Date().time + 604800000L)
        GlobalScope.launch(Dispatchers.IO) {

            try{
                val data = nasaApi?.getAsteroidData(startFormattedDate,endFormattedDate,getString(R.string.API_KEY))?.await()

                val json = JSONObject(data.toString())
                val asteroidList = parseAsteroidsJsonResult(json)

                val asteroidEntityList = mutableListOf<AsteroidEntity>()
                asteroidList.forEach {
                    asteroidEntityList.add(it.asDatabaseModel())
                }


                database.asteroidDatabaseDao.insertAllAsteroids(asteroidEntityList)

                Timber.d("check ${database.asteroidDatabaseDao.getAllAsteroids()}")



            }catch (e:Exception){
                Timber.e("exception getting data. $e")
            }
        }
    }
}
