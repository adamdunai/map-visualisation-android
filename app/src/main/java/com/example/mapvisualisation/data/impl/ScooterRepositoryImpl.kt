package com.example.mapvisualisation.data.impl

import com.example.mapvisualisation.api.ApiClient
import com.example.mapvisualisation.common.mapper.toDataModelList
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.database.AppDatabase
import com.example.mapvisualisation.database.tuple.ScooterInfoTuple
import com.example.mapvisualisation.database.tuple.ScooterMapTuple
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScooterRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient,
    private val appDatabase: AppDatabase,
) : ScooterRepository {

    override val scooterListStream: Flow<List<ScooterMapTuple>> =
        appDatabase.scooterDao().getScooterList()

    override suspend fun fetchScooterData() {
        val response = apiClient.getScooterList()

        appDatabase.scooterDao().insertScooterList(
            response.data.current.toDataModelList()
        )
    }

    override suspend fun getScooterInfo(scooterId: String): ScooterInfoTuple =
        appDatabase.scooterDao().getScooterInfo(scooterId)
}
