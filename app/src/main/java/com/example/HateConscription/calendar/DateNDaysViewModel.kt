package com.example.HateConscription.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateNDaysViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DateNDaysUiState())
    val uiState: StateFlow<DateNDaysUiState> = _uiState.asStateFlow()

    private val dateRegex: String = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})\\/(((0[13578]|1[02])\\/(0[1-9]|[12][0-9]|3[01]))|"+
            "((0[469]|11)\\/(0[1-9]|[12][0-9]|30))|(02\\/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
            "((0[48]|[2468][048]|[3579][26])00))\\/02\\/29)$"

    var dDay by mutableStateOf("")
        private set

    fun updateBirthdayInput (bDay: String) {
        if (Regex(dateRegex).matches(bDay)) {
            _uiState.update { currentState ->
                currentState.copy(birthdaySelect = bDay)
            }
            isIllegalBirthdayInputState(false)
        }  else {
            isIllegalBirthdayInputState(true)
        }
    }

    fun updateEnlistmentDay (eDay: String) {
        if (Regex(dateRegex).matches(eDay) &&
            (eDay.take(4).toInt() - _uiState.value.birthdaySelect.take(4).toInt()) > 18) {
            _uiState.update { currentState ->
                currentState.copy(enlistmentDaySelect = eDay)
            }
            isIllegalEnlistmentDayInputState(false)
        } else if ((eDay.take(4).toInt() - _uiState.value.birthdaySelect.take(4).toInt()) < 18) {
            isIllegalEnlistmentDayInputState(true)
        } else {
            isIllegalEnlistmentDayInputState(true)
        }
    }

    fun updateDDay (_dDay: String) {
        dDay = _dDay
        _uiState.update { currentState ->
            currentState.copy(dDay = dDay)
        }
    }

    private fun isIllegalBirthdayInputState (current: Boolean) {
        _uiState.update { state ->
            state.copy(isIllegalBirthdayDate = current)
        }
    }

    private fun isIllegalEnlistmentDayInputState (current: Boolean) {
        _uiState.update { state ->
            state.copy(isIllegalEnlistmentDate = current)
        }
    }

    private fun showDetail (current: Boolean) {
        _uiState.update { state ->
            state.copy(show = current)
        }
    }
    fun errorState (current: Boolean) {
        _uiState.update { state ->
            state.copy(error = current)
        }
    }

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    private val today: LocalDate = LocalDate.now()

    private fun calculateWhichDay2Leave (birthday: String, eDate: String, dDay: String): String {
        val birthYear = birthday.take(4).toInt()
        val totalDays: Int
        val theDayBackHome: String
        return if (birthYear in 1995..2004) {
            totalDays = 112 - dDay.toInt()
            theDayBackHome = LocalDate.parse(eDate, formatter)
                .plusDays(totalDays.toLong()).format(formatter).toString()
            theDayBackHome
        } else {
            totalDays = 364 - dDay.toInt()
            theDayBackHome = LocalDate.parse(eDate, formatter)
                .plusDays(totalDays.toLong()).format(formatter).toString()
            theDayBackHome
        }
    } // 計算哪一天退伍

    private fun backHomeCountDown (localDate: LocalDate, lastDay: String): Long {
        val _lastDay = LocalDate.parse(lastDay, formatter)
        return ChronoUnit.DAYS.between(localDate, _lastDay)
    } // 計算還剩幾天

    fun onDateSubmit () {
        if (_uiState.value.birthdaySelect != "" && _uiState.value.enlistmentDaySelect != ""
            &&_uiState.value.dDay != "") {
            errorState(false)
            val lastDaysDate = calculateWhichDay2Leave(
                _uiState.value.birthdaySelect,
                _uiState.value.enlistmentDaySelect,
                _uiState.value.dDay
            )
            val dayCountDown = backHomeCountDown(today, lastDaysDate)
            _uiState.update { value ->
                value.copy(day2Leave = lastDaysDate, backHomeCountDown = dayCountDown)
            }
            showDetail(true)
        } else {
            showDetail(false)
            errorState(true)
        }
    }
}