package com.example.mapvisualisation.api

import com.example.mapvisualisation.api.model.ScooterResponseApiModel
import retrofit2.http.GET

interface ApiClient {

    @GET("b/5fa8ff8dbd01877eecdb898f")
    suspend fun getScooterList(): ScooterResponseApiModel
}
