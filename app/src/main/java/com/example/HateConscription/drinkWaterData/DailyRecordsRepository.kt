package com.example.HateConscription.drinkWaterData

import kotlinx.coroutines.flow.Flow

interface DailyRecordsRepository {
    suspend fun addData(drinkWaterDataState: DrinkWaterDataState)

    suspend fun updateData(drinkWaterDataState: DrinkWaterDataState)

    fun getItemData(timeStamped: String): Flow<DrinkWaterDataState>

}