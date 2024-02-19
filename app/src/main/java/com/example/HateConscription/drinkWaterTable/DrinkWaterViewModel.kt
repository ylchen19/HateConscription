package com.example.HateConscription.drinkWaterTable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.HateConscription.calendar.DateNDaysUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DrinkWaterViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DrinkWaterUiState())
    val uiState: StateFlow<DrinkWaterUiState> = _uiState.asStateFlow()
    private val DateUiState = MutableStateFlow(DateNDaysUiState())

    var userInput by mutableStateOf("")
        private set

    fun updateFormDate (input: String) {
        _uiState.update { current ->
            current.copy(data = input)
        }
    }


}