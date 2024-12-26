package com.example.skycheck.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val locality: String,
    val latitude: Double,
    val longitude: Double,
)
