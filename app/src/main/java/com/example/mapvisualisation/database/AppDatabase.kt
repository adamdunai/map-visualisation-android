package com.example.mapvisualisation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mapvisualisation.BuildConfig
import com.example.mapvisualisation.database.dao.ScooterDao
import com.example.mapvisualisation.database.model.ScooterDataModel

@Database(
    entities = [
        ScooterDataModel::class
    ],
    version = BuildConfig.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scooterDao(): ScooterDao
}
