package com.example.mapvisualisation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mapvisualisation.database.model.ScooterDataModel
import com.example.mapvisualisation.database.tuple.ScooterDetailsTuple
import com.example.mapvisualisation.database.tuple.ScooterMapTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface ScooterDao {

    @Insert
    suspend fun insertScooterList(scooterList: List<ScooterDataModel>)

    @Query("SELECT id, latitude, longitude FROM Scooters")
    fun getScooterList(): Flow<List<ScooterMapTuple>>

    @Query(
        "SELECT id, vehicleId, zoneId, battery, state, fleetbirdId" +
            " FROM Scooters" +
            " WHERE id = :id"
    )
    suspend fun getScooter(id: String): ScooterDetailsTuple
}
