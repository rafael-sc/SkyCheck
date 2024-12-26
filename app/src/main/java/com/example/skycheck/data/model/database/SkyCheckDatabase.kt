package com.example.skycheck.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skycheck.data.model.dao.LocationDao
import com.example.skycheck.data.model.entity.Location

@Database(entities = [Location::class], version = 1)
abstract class SkyCheckDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
}