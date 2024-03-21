package com.example.HateConscription.drinkWaterTable

import androidx.lifecycle.ViewModel
import com.example.HateConscription.drinkWaterData.DrinkWaterDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DrinkWaterViewModel @Inject constructor(
): ViewModel() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

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

data class FormContent(
    val content: DrinkWaterDataState = DrinkWaterDataState(),
    val isEntryValid: Boolean = false
)