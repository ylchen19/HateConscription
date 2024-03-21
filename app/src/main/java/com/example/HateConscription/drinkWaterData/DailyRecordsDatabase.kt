package com.example.HateConscription.drinkWaterData

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [DrinkWaterDataState::class], version = 1, exportSchema = false)
abstract class DailyRecordsDatabase: RoomDatabase() {
    abstract fun dao(): DailyRecordsDao

}