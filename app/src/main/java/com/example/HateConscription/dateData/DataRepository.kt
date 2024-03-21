package com.example.HateConscription.dateData

import com.example.HateConscription.calendar.DateNDaysDataState
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun setData(
        id: Int,
        birthdaySelect: String,
        enlistmentDaySelect: String,
        dDay: String,
        day2Leave: String,
        show: Boolean,
    )

    suspend fun getData(): Flow<DateNDaysDataState>
}