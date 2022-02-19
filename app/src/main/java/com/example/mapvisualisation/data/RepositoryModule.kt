package com.example.mapvisualisation.data

import com.example.mapvisualisation.data.impl.ScooterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindScooterRepository(repository: ScooterRepositoryImpl): ScooterRepository
}
