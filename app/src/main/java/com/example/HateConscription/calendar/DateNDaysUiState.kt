package com.example.HateConscription.calendar

data class DateNDaysUiState(
    val birthdaySelect: String = "",
    val enlistmentDaySelect: String = "",
    val dDay: String = "0",
    val day2Leave: String = "",
    val backHomeCountDown: Long = 0,
    val isIllegalBirthdayDate: Boolean = false,
    val isIllegalEnlistmentDate: Boolean = false,
    val show: Boolean = false,
    val error: Boolean = false,
)
