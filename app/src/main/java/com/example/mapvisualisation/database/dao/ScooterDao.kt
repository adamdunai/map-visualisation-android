package com.example.mapvisualisation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mapvisualisation.database.model.ScooterDataModel
import com.example.mapvisualisation.database.tuple.ScooterInfoTuple
import com.example.mapvisualisation.database.tuple.ScooterMapTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface ScooterDao {

    /**
     * Data coming from the API is static, that's why using [OnConflictStrategy.IGNORE]
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScooterList(scooterList: List<ScooterDataModel>)

    @Query("SELECT id, latitude, longitude FROM Scooters")
    fun getScooterList(): Flow<List<ScooterMapTuple>>

    @Query(
        "SELECT id, vehicleId, zoneId, battery, state, fleetbirdId" +
            " FROM Scooters" +
            " WHERE id = :id"
    )
    suspend fun getScooterInfo(id: String): ScooterInfoTuple
}
