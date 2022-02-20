package com.example.mapvisualisation.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapvisualisation.common.di.IoDispatcher
import com.example.mapvisualisation.common.mapper.toUiModel
import com.example.mapvisualisation.data.ScooterRepository
import com.example.mapvisualisation.main.model.ScooterInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScooterInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ScooterRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScooterInfoUiModel?>(null)
    val uiState: StateFlow<ScooterInfoUiModel?> get() = _uiState

    init {
        getInfo()
    }

    private fun getInfo() {
        savedStateHandle.get<String>(KEY_SCOOTER_ID)?.let { scooterId ->
            viewModelScope.launch(ioDispatcher) {
                _uiState.value = repository.getScooterInfo(scooterId).toUiModel()
            }
        }
    }

    companion object {
        const val KEY_SCOOTER_ID = "scooterId"
    }
}
