package com.example.HateConscription.drinkWaterTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.HateConscription.drinkWaterData.DailyRecordsRepository
import com.example.HateConscription.drinkWaterData.DrinkWaterDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DrinkWaterViewModel @Inject constructor(
    private val dailyRecordsRepositoryImpl: DailyRecordsRepository
): ViewModel() {

    private val _dataState = MutableStateFlow(DrinkWaterDataState())
    val dataState: StateFlow<DrinkWaterDataState> = _dataState.asStateFlow()

    fun getDailyRecords(timeStamped: String) = viewModelScope.launch(Dispatchers.IO) {
        dailyRecordsRepositoryImpl.getItemData(timeStamped).collect{ value ->
            _dataState.update { current ->
                current.copy(
                    dateStamped = value.dateStamped,
                    bodyWeight = value.bodyWeight,
                    bodyTemperature = value.bodyTemperature,
                    water1 = value.water1,
                    water2 = value.water2,
                    water3 = value.water3,
                    water4 = value.water4,
                    totalWater = value.totalWater
                )
            }
        }
    }

    fun saveDailyRecords(drinkWaterDataState: DrinkWaterDataState) =
        viewModelScope.launch(Dispatchers.IO) {
            dailyRecordsRepositoryImpl.addData(drinkWaterDataState)
    }

    fun updateBodyWeight (weight: String) {
        _dataState.update { current ->
            current.copy(bodyWeight = weight)
        }
    }

    fun updateBodyTemperature (temp: String) {
        _dataState.update { current ->
            current.copy(bodyTemperature = temp)
        }
    }

    fun updateWater1 (water1: String) {
        _dataState.update { current ->
            current.copy(water1 = water1)
        }
    }

    fun updateWater2 (water2: String) {
        _dataState.update { current ->
            current.copy(water2 = water2)
        }
    }

    fun updateWater3 (water3: String) {
        _dataState.update { current ->
            current.copy(water3 = water3)
        }
    }

    fun updateWater4 (water4: String) {
        _dataState.update { current ->
            current.copy(water4 = water4)
        }
    }

    fun updateWaterTotal (totalWater: String) {
        _dataState.update { current ->
            current.copy(totalWater = totalWater)
        }
    }
    fun updateTheForm(date: String) {
        _dataState.update { current ->
            current.copy(dateStamped = date)
        }
    }

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    private val today: String = LocalDate.now().format(formatter).toString()

    fun getPerDay(start: String?, end: String?): List<String> {
        val daysList = mutableListOf<String>()
        return if (start != null && end != null) {
            val days = ChronoUnit
                .DAYS
                .between(
                    LocalDate.parse(start, formatter),
                    LocalDate.parse(end, formatter)
                )
            for (i in 0..days) {
                val date = LocalDate.parse(start, formatter).plusDays(i).format(formatter)
                daysList.add(date)
            }
            daysList
        } else {
            emptyList()
        }
    }
}