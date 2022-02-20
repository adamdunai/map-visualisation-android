package com.example.mapvisualisation.viewmodel

import com.example.mapvisualisation.CoroutineDispatcherRule
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.main.model.ScooterMapUiState
import com.example.mapvisualisation.main.viewmodel.ScooterMapViewModel
import com.example.mapvisualisation.service.LocationService
import com.example.mapvisualisation.service.NetworkService
import com.example.mapvisualisation.test
import com.squareup.moshi.JsonDataException
import io.kotest.matchers.collections.shouldEndWith
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO migrate runBlockingTest
@ExperimentalCoroutinesApi
class ScooterMapViewModelUnitTest {

    @get:Rule
    val rule = CoroutineDispatcherRule()

    @MockK
    lateinit var repository: ScooterRepository

    @MockK
    lateinit var networkService: NetworkService

    @MockK
    lateinit var locationService: LocationService

    lateinit var viewModel: ScooterMapViewModel

    @Before
    fun initMockKAnnotations() {
        MockKAnnotations.init(this, relaxed = true)

        viewModel = ScooterMapViewModel(
            repository = repository,
            networkService = networkService,
            locationService = locationService,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun fetchScooterList_hasNetwork_success() = runBlockingTest {
        every { networkService.isConnected() } returns true

        val (loadingEvents, loadingObserver) = viewModel.uiState.test(this)

        viewModel.fetchScooterList()

        loadingObserver.cancel()

        loadingEvents shouldEndWith listOf(
            ScooterMapUiState.Loading,
            ScooterMapUiState.Success
        )
    }

    @Test
    fun fetchScooterList_noNetwork_networkError() = runBlockingTest {
        every { networkService.isConnected() } returns false

        val (loadingEvents, loadingObserver) = viewModel.uiState.test(this)

        viewModel.fetchScooterList()

        loadingObserver.cancel()

        loadingEvents shouldEndWith listOf(
            ScooterMapUiState.Loading,
            ScooterMapUiState.NetworkError
        )
    }

    @Test
    fun fetchScooterList_hasNetwork_error() = runBlockingTest {
        every { networkService.isConnected() } returns true
        coEvery { repository.fetchScooterData() } throws JsonDataException()

        val (loadingEvents, loadingObserver) = viewModel.uiState.test(this)

        viewModel.fetchScooterList()

        loadingObserver.cancel()

        loadingEvents shouldEndWith listOf(
            ScooterMapUiState.Loading,
            ScooterMapUiState.Error
        )
    }
}
