package com.example.mapvisualisation.main.model

sealed class ScooterMapUiState {
    object Loading : ScooterMapUiState()
    object Success : ScooterMapUiState()
    object NetworkError : ScooterMapUiState()
    object Error : ScooterMapUiState()
}
