package com.example.mapvisualisation.database.tuple

data class ScooterDetailsTuple(
    val id: String,
    val vehicleId: String,
    val zoneId: String,
    val battery: Int,
    val state: String,
    val fleetbirdId: Int,
)
