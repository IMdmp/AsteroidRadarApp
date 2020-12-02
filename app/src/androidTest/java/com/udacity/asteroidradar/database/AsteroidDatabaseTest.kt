package com.udacity.asteroidradar.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidDatabaseTest {
    private lateinit var asteroidDatabaseDao: AsteroidDatabaseDao
    private lateinit var db: AsteroidDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        asteroidDatabaseDao = db.asteroidDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAsteroid() = runBlocking {
        val asteroid = AsteroidEntity(
            1,
            "codename",
            "2012-12-12",
            10.0,
            20.0,
            30.0,
            400000.0,
            false
        )
        asteroidDatabaseDao.insert(asteroid)

        val asteroidEntity = asteroidDatabaseDao.getAsteroid()
        assertEquals(asteroidEntity?.id, asteroid.id)
    }


}