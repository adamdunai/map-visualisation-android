package com.example.mapvisualisation.mapper

import com.example.mapvisualisation.api.model.ScooterItemResponseApiModel
import com.example.mapvisualisation.common.mapper.toDataModel
import com.example.mapvisualisation.database.model.ScooterDataModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperUnitTest {

    @Test
    fun scooterItemResponseApiModelMapper_isCorrect() {
        val apiModel = ScooterItemResponseApiModel(
            id = "6348dfa0-1b20-40ed-98e9-fe9e232b6105",
            vehicleId = "8ece0495-bef0-4eac-a58e-dede2bf975a3",
            zoneId = "BERLIN",
            battery = 91,
            state = "ACTIVE",
            fleetbirdId = 118160,
            latitude = 52.506731,
            longitude = 13.289618
        )

        assertEquals(
            ScooterDataModel(
                id = "6348dfa0-1b20-40ed-98e9-fe9e232b6105",
                vehicleId = "8ece0495-bef0-4eac-a58e-dede2bf975a3",
                zoneId = "BERLIN",
                battery = 91,
                state = "ACTIVE",
                fleetbirdId = 118160,
                latitude = 52.506731,
                longitude = 13.289618
            ),
            apiModel.toDataModel()
        )
    }
}
