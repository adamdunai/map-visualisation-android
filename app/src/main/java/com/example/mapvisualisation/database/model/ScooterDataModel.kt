package com.example.mapvisualisation.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Scooters")
data class ScooterDataModel(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val zoneId: String,
    val battery: Int,
    val state: String,
    val fleetbirdId: Int,
    val latitude: Double,
    val longitude: Double,
)
