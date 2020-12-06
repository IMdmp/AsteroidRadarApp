package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.NetworkNasaPicture
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<AsteroidApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<AsteroidApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the MarsProperty with
    // new values
    private val _asteroidImage = MutableLiveData<NetworkNasaPicture>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val asteroidImage: LiveData<NetworkNasaPicture>
        get() = _asteroidImage



    var database = AsteroidDatabase.getDatabase(application)

    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            asteroidRepository.refreshAsteroids()
            try {
                _asteroidImage.value =
                    asteroidRepository.getImageOfTheDay()
                _status.value = AsteroidApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AsteroidApiStatus.ERROR

            }
        }
    }

    val asteroidList = asteroidRepository.asteroids

    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}