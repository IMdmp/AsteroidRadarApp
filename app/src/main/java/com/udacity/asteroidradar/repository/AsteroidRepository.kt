package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.base.Constants
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.NetworkNasaPicture
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val asteroidDatabase: AsteroidDatabase) {

    val asteroids: LiveData<List<AsteroidEntity>> =
        asteroidDatabase.asteroidDatabaseDao.getAllAsteroids()

    suspend fun getImageOfTheDay(): NetworkNasaPicture? {
        var networkNasaPicture: NetworkNasaPicture? =null
        withContext(Dispatchers.IO){
            try{
                networkNasaPicture = Network.nasaApi.getPictureOfTheDay(Constants.API_KEY).await()
                networkNasaPicture?.let {
                    asteroidDatabase.asteroidDatabaseDao.insert(it.asEntity())
                }
                return@withContext
            }catch (e:java.lang.Exception){

            }
        }

        return networkNasaPicture
    }
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