package com.example.mapvisualisation.service

import android.content.Context
import com.example.mapvisualisation.service.impl.LocationServiceImpl
import com.example.mapvisualisation.service.impl.NetworkServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Singleton
    @Provides
    fun provideNetworkService(@ApplicationContext context: Context): NetworkService =
        NetworkServiceImpl(context)

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideLocationService(@ApplicationContext context: Context): LocationService =
        LocationServiceImpl(context)
}
