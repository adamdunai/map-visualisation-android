package com.example.mapvisualisation.common.mapper

import com.example.mapvisualisation.api.model.ScooterItemResponseApiModel
import com.example.mapvisualisation.database.model.ScooterDataModel

fun ScooterItemResponseApiModel.toDataModel() =
    ScooterDataModel(
        id = id,
        vehicleId = vehicleId,
        zoneId = zoneId,
        battery = battery,
        state = state,
        fleetbirdId = fleetbirdId,
        latitude = latitude,
        longitude = longitude
    )

fun List<ScooterItemResponseApiModel>.toDataModelList() =
    map { it.toDataModel() }
