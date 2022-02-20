package com.example.mapvisualisation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.mapvisualisation.CoroutineDispatcherRule
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.database.tuple.ScooterInfoTuple
import com.example.mapvisualisation.main.model.ScooterInfoUiModel
import com.example.mapvisualisation.main.viewmodel.ScooterInfoViewModel
import com.example.mapvisualisation.test
import io.kotest.matchers.collections.shouldEndWith
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO migrate runBlockingTest
@ExperimentalCoroutinesApi
class ScooterInfoViewModelUnitTest {

    @get:Rule
    val rule = CoroutineDispatcherRule()

    @MockK
    lateinit var repository: ScooterRepository

    lateinit var viewModel: ScooterInfoViewModel

    @Before
    fun initMockKAnnotations() {
        MockKAnnotations.init(this, relaxed = true)

        val savedStateHandle = SavedStateHandle().apply {
            set(ScooterInfoViewModel.KEY_SCOOTER_ID, "1234")
        }

        coEvery { repository.getScooterInfo("1234") } returns ScooterInfoTuple(
            id = "6348dfa0-1b20-40ed-98e9-fe9e232b6105",
            vehicleId = "8ece0495-bef0-4eac-a58e-dede2bf975a3",
            zoneId = "BERLIN",
            battery = 91,
            state = "ACTIVE",
            fleetbirdId = 118160,
        )

        viewModel = ScooterInfoViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun init_success() = runBlockingTest {
        val (loadingEvents, loadingObserver) = viewModel.uiState.test(this)

        loadingObserver.cancel()

        loadingEvents shouldEndWith ScooterInfoUiModel(
            zoneId = "BERLIN",
            battery = 91,
            state = "ACTIVE",
            fleetbirdId = 118160,
        )
    }
}
