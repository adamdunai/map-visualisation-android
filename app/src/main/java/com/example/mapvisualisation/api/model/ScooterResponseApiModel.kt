package com.example.mapvisualisation.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScooterResponseApiModel(
    val data: ScooterDataResponseApiModel,
)
