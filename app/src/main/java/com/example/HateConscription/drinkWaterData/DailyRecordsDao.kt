package com.example.HateConscription.drinkWaterData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drinkWaterDataState: DrinkWaterDataState)

    @Update
    suspend fun update(drinkWaterDataState: DrinkWaterDataState)

    @Query("SELECT * FROM daily_records WHERE dateStamped = :dateStamped")
    fun getItemData(dateStamped: String): Flow<DrinkWaterDataState>

    @Query("SELECT * FROM daily_records")
    suspend fun findAll(): List<DrinkWaterDataState>
}