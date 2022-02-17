package com.example.mapvisualisation.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScooterItemResponseApiModel(
    val id: String,
    val vehicleId: String,
    val zoneId: String,
    val battery: Int,
    val state: String,
    val fleetbirdId: Int,
    val latitude: Double,
    val longitude: Double,
)
