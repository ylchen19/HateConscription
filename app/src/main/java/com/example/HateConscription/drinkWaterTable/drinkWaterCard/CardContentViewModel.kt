package com.example.HateConscription.drinkWaterTable.drinkWaterCard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.HateConscription.drinkWaterData.DailyRecordsRepositoryImpl
import com.example.HateConscription.drinkWaterData.DrinkWaterDataState
import com.example.HateConscription.drinkWaterData.toFormContent
import com.example.HateConscription.drinkWaterTable.FormContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CardContentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dailyRecordsRepositoryImpl: DailyRecordsRepositoryImpl
): ViewModel() {

    private val cardDate: String = checkNotNull(savedStateHandle["date"])
    var inputState by mutableStateOf(FormContent())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            inputState = dailyRecordsRepositoryImpl.getItemData(cardDate)
                .filterNotNull()
                .first()
                .toFormContent(true)
        }
    }

    suspend fun updateDailyRecords() {
        if (validateInput(inputState.content)) {
            dailyRecordsRepositoryImpl.updateData(inputState.content)
        }
    }

    private fun validateInput(uiState: DrinkWaterDataState = inputState.content): Boolean {
        return with(uiState) {
            dateStamped.isNotBlank() && bodyWeight.isNotBlank() && bodyTemperature.isNotBlank()
                    && water1.isNotBlank() && water2.isNotBlank() && water3.isNotBlank()
                    && water4.isNotBlank() && totalWater.isNotBlank()
        }
    }

    suspend fun saveDailyRecords() {
        if (validateInput()) {
            viewModelScope.launch(Dispatchers.IO) {
                dailyRecordsRepositoryImpl.addData(inputState.content)
            }
        }
    }

    fun updateTheForm(
        dateState: DrinkWaterDataState
    ) {
        inputState =
            FormContent(content = dateState, isEntryValid = validateInput(dateState))
    }
}