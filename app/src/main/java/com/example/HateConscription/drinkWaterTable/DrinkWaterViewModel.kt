package com.example.HateConscription.drinkWaterTable

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DrinkWaterViewModel: ViewModel() {

    private val _dataState = MutableStateFlow(DrinkWaterDataState())
    val dataState: StateFlow<DrinkWaterDataState> = _dataState.asStateFlow()
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
    fun updateTheForm() {
        _dataState.update { current ->
            current.copy(dateStamped = today)
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