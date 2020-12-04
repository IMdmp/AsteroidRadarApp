package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.Network
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.java.Constant

class AsteroidRepository(private val asteroidDatabase: AsteroidDatabase) {

    val asteroids: List<AsteroidEntity> =
        asteroidDatabase.asteroidDatabaseDao.getAllAsteroids()


    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val df = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            val startFormattedDate: String = df.format(Date())
            val endFormattedDate: String = df.format(Date().time + 604800000L)

            try {
                val data = Network.nasaApi.getAsteroidData(
                    startFormattedDate, endFormattedDate,Constants.API_KEY
                ).await()

                val json = JSONObject(data.toString())
                val asteroidList = parseAsteroidsJsonResult(json)
                val asteroidEntityList = mutableListOf<AsteroidEntity>()
                asteroidList.forEach {
                    asteroidEntityList.add(it.asDatabaseModel())
                }
                asteroidDatabase.asteroidDatabaseDao.insertAllAsteroids(asteroidEntityList)
            } catch (e: Exception) {
                Timber.e("exception getting data. $e")
            }

        }
    }
}