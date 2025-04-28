package com.example.skycheck.data.model.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skycheck.data.model.database.SkyCheckDatabase
import com.example.skycheck.data.model.entity.Location
import com.example.skycheck.data.model.mock.locationsMock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {

    @get:Rule
    val taskExecutor: TestRule = InstantTaskExecutorRule()

    private lateinit var database: SkyCheckDatabase
    private lateinit var dao: LocationDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = SkyCheckDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.locationDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertLocation() = runTest {
        val location = locationsMock[0]
        dao.saveLocation(location = location)
        val locations = dao.getLocations().first()

        assert(locations.contains(location))
    }

    @Test
    fun testDeleteLocation() = runTest {
        val location = locationsMock[0]
        dao.saveLocation(location = location)
        dao.deleteLocation(location = location)
        val locations = dao.getLocations().first()

        assert(!locations.contains(location))
        assert(locations.isEmpty())
    }

    @Test
    fun testGetLocations() = runTest {
        val location1 = locationsMock[0]
        val location2 = locationsMock[1]

        dao.saveLocation(location = location1)
        dao.saveLocation(location = location2)

        val locations = dao.getLocations().first()

        assert(locations.size == 2)
    }
}