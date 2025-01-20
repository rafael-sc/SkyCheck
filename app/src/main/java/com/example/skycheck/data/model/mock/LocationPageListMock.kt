package com.example.skycheck.data.model.mock

import com.example.skycheck.data.model.entity.Location

var locationsPageMock = listOf(
    Location(
        id = 1,
        locality = "São Paulo",
        latitude = -23.5506507,
        longitude = -46.6333824,
        isCurrentUserLocality = true
    ),
    Location(
        id = 2,
        locality = "Taubaté",
        latitude = -23.031448,
        longitude = -45.5612792,
        isCurrentUserLocality = false
    ),
    Location(
        id = 3,
        locality = "Gold Coast",
        latitude = -28.0023731,
        longitude = 153.4145987,
        isCurrentUserLocality = false
    ),
)