package com.example.HateConscription.calendar

data class DateNDaysUiState(
    val isIllegalBirthdayDate: Boolean = false,
    val isIllegalEnlistmentDate: Boolean = false,
    val error: Boolean = false,
    val backHomeCountDown: Long = 0
)
