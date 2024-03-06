package com.example.HateConscription.drinkWaterData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [DrinkWaterDataState::class], version = 1, exportSchema = false)
abstract class DailyRecordsDatabase: RoomDatabase() {
    abstract fun dao(): DailyRecordsDao

}