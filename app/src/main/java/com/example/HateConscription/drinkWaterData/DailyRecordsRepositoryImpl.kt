package com.example.HateConscription.drinkWaterData

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyRecordsRepositoryImpl @Inject constructor(
    private val dao: DailyRecordsDao
): DailyRecordsRepository {
    override suspend fun getAllData(): List<DrinkWaterDataState> {
        return dao.findAll()
    }

    override suspend fun getItemData(timeStamped: String): Flow<DrinkWaterDataState> {
        return dao.getItemData(timeStamped)
    }

    override suspend fun addData(drinkWaterDataState: DrinkWaterDataState) {
        dao.insert(drinkWaterDataState = drinkWaterDataState)
    }
}