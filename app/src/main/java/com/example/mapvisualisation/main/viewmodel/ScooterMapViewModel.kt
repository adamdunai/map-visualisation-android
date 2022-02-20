package com.example.mapvisualisation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapvisualisation.common.di.IoDispatcher
import com.example.mapvisualisation.common.mapper.toClusterItem
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.main.model.ScooterClusterItem
import com.example.mapvisualisation.main.model.ScooterMapUiState
import com.example.mapvisualisation.service.LocationService
import com.example.mapvisualisation.service.NetworkService
import com.example.mapvisualisation.service.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScooterMapViewModel @Inject constructor(
    private val repository: ScooterRepository,
    private val networkService: NetworkService,
    private val locationService: LocationService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScooterMapUiState>(ScooterMapUiState.Loading)
    val uiState: StateFlow<ScooterMapUiState> = _uiState

    val scooterListFlow: Flow<List<ScooterClusterItem>> =
        repository.scooterListStream.map { list ->
            list.map { it.toClusterItem() }
        }

    val locationFlow: Flow<Location> = flow {
        val location = locationService.getCurrentLocation()
        emit(location)
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    init {
        fetchScooterList()
    }

    fun fetchScooterList() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = ScooterMapUiState.Loading
            if (!networkService.isConnected()) {
                _uiState.value = ScooterMapUiState.NetworkError
                return@launch
            }

            try {
                repository.fetchScooterData()
                _uiState.value = ScooterMapUiState.Success
            } catch (exception: Exception) {
                _uiState.value = ScooterMapUiState.Error
            }
        }
    }
}
