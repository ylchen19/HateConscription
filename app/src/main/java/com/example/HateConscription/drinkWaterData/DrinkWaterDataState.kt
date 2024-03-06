package com.example.HateConscription.drinkWaterData

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "daily_records")
data class DrinkWaterDataState(
    @PrimaryKey
    val dateStamped: String = "",
    val bodyWeight: String = "",
    val bodyTemperature: String = "",
    val water1: String = "",
    val water2: String = "",
    val water3: String = "",
    val water4: String = "",
    val totalWater: String = ""
)