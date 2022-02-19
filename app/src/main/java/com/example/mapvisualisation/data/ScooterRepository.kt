package com.example.mapvisualisation.data

import com.example.mapvisualisation.database.tuple.ScooterMapTuple
import kotlinx.coroutines.flow.Flow

interface ScooterRepository {

    val scooterListStream: Flow<List<ScooterMapTuple>>

    suspend fun fetchScooterData()
}
