package com.example.HateConscription.calendar

import kotlinx.serialization.Serializable

@Serializable
data class DateNDaysDataState(
    val id: Int = 0,
    val birthdaySelect: String = "",
    val enlistmentDaySelect: String = "",
    val dDay: String = "0",
    val day2Leave: String = "",
    val show: Boolean = false,
    val saved: Boolean = false
)
