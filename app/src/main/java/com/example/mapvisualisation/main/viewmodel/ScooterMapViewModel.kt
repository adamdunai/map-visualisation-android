package com.example.mapvisualisation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapvisualisation.common.di.IoDispatcher
import com.example.mapvisualisation.common.mapper.toClusterItem
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.main.model.ScooterClusterItem
import com.example.mapvisualisation.main.model.ScooterMapUiState
import com.example.mapvisualisation.service.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScooterMapViewModel @Inject constructor(
    private val repository: ScooterRepository,
    private val networkService: NetworkService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScooterMapUiState>(ScooterMapUiState.Loading)
    val uiState: StateFlow<ScooterMapUiState> = _uiState

    init {
        fetchScooterList()
    }

    val scooterListFlow: Flow<List<ScooterClusterItem>> =
        repository.scooterListStream.map { list ->
            list.map { it.toClusterItem() }
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
