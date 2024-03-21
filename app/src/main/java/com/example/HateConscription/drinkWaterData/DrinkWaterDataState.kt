package com.example.HateConscription.drinkWaterData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.HateConscription.drinkWaterTable.FormContent

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

fun DrinkWaterDataState.toFormContent(isEntryValid: Boolean = false): FormContent = FormContent(
    content = this,
    isEntryValid = isEntryValid
)