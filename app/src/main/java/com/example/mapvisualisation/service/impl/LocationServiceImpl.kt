package com.example.mapvisualisation.service.impl

import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.mapvisualisation.service.LocationService
import com.example.mapvisualisation.service.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class LocationServiceImpl(
    context: Context,
) : LocationService {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val cancellationTokenSource: CancellationTokenSource =
        CancellationTokenSource()

    @RequiresPermission(
        anyOf = [
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        ]
    )
    override suspend fun getCurrentLocation(): Location {
        val currentLocation = fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .await(cancellationTokenSource)

        return Location(currentLocation.latitude, currentLocation.longitude)
    }
}
