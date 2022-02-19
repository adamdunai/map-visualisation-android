package com.example.mapvisualisation.common.mapper

import com.example.mapvisualisation.database.tuple.ScooterMapTuple
import com.example.mapvisualisation.main.model.ScooterClusterItem

fun ScooterMapTuple.toClusterItem() =
    ScooterClusterItem(
        id = id,
        latitude = latitude,
        longitude = longitude
    )
