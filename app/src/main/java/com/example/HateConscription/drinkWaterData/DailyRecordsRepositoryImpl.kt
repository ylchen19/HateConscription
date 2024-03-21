package com.example.HateConscription.drinkWaterData

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyRecordsRepositoryImpl @Inject constructor(
    private val dao: DailyRecordsDao
): DailyRecordsRepository {

    override fun getItemData(timeStamped: String): Flow<DrinkWaterDataState> {
        return dao.getItemData(timeStamped)
    }

    override suspend fun addData(drinkWaterDataState: DrinkWaterDataState) {
        dao.insert(drinkWaterDataState = drinkWaterDataState)
    }

    override suspend fun updateData(drinkWaterDataState: DrinkWaterDataState) {
        dao.update(drinkWaterDataState = drinkWaterDataState)
    }
}