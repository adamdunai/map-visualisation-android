package com.example.mapvisualisation.service

import com.example.mapvisualisation.service.model.Location

interface LocationService {

    /**
     * Returns current location
     */
    suspend fun getCurrentLocation(): Location
}
