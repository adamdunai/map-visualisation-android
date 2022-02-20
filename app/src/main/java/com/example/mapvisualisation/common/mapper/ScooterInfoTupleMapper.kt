package com.example.mapvisualisation.common.mapper

import com.example.mapvisualisation.database.tuple.ScooterInfoTuple
import com.example.mapvisualisation.main.model.ScooterInfoUiModel

fun ScooterInfoTuple.toUiModel() =
    ScooterInfoUiModel(
        zoneId = zoneId,
        battery = battery,
        state = state,
        fleetbirdId = fleetbirdId
    )
