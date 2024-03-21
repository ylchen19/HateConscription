package com.example.HateConscription.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.HateConscription.dateData.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
@HiltViewModel
class DateNDaysViewModel @Inject constructor(
    private val dataStateRepository: DataRepository,
): ViewModel() {

    init {
        getDataState()
    }

    private val _uiState = MutableStateFlow(DateNDaysUiState())
    val uiState: StateFlow<DateNDaysUiState> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(DateNDaysDataState())
    val dataState: StateFlow<DateNDaysDataState> = _dataState.asStateFlow()

    private val dateRegex: String = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})\\/(((0[13578]|1[02])\\/(0[1-9]|[12][0-9]|3[01]))|"+
            "((0[469]|11)\\/(0[1-9]|[12][0-9]|30))|(02\\/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|"+
            "((0[48]|[2468][048]|[3579][26])00))\\/02\\/29)$"

    private fun getDataState() = viewModelScope.launch{
        dataStateRepository.getData().collect { values ->
            _dataState.update { state ->
                state.copy(
                    id = values.id,
                    birthdaySelect = values.birthdaySelect,
                    enlistmentDaySelect = values.enlistmentDaySelect,
                    dDay = values.dDay,
                    day2Leave = values.day2Leave,
                    show = values.show,
                )
            }
            dDay = values.dDay
            birthdaySelected = values.birthdaySelect
            enlistmentDaySelected = values.enlistmentDaySelect
        }
    }

    private fun saveData (
        id: Int,
        birthdaySelect: String,
        enlistmentDaySelect: String,
        dDay: String,
        day2Leave: String,
        show: Boolean,
    ) = viewModelScope.launch{
        dataStateRepository.setData(
            id = id,
            birthdaySelect = birthdaySelect,
            enlistmentDaySelect = enlistmentDaySelect,
            dDay = dDay,
            day2Leave = day2Leave,
            show = show,
        )
    }

    var birthdaySelected by mutableStateOf("")
        private set

    var enlistmentDaySelected by mutableStateOf("")
        private set

    var dDay by mutableStateOf("")
        private set

    fun updateBirthdayInput (bDay: String) {
        if (Regex(dateRegex).matches(bDay)) {
            _dataState.update { currentState ->
                currentState.copy(birthdaySelect = bDay)
            }
            isIllegalBirthdayInputState(false)
        }  else {
            isIllegalBirthdayInputState(true)
        }
    }

    fun updateEnlistmentDay (eDay: String) {
        if (Regex(dateRegex).matches(eDay) &&
            (eDay.take(4).toInt() - _dataState.value.birthdaySelect.take(4).toInt()) > 18) {
            _dataState.update { currentState ->
                currentState.copy(enlistmentDaySelect = eDay)
            }
            isIllegalEnlistmentDayInputState(false)
        } else if ((eDay.take(4).toInt() - _dataState.value.birthdaySelect.take(4).toInt()) < 18) {
            isIllegalEnlistmentDayInputState(true)
        } else {
            isIllegalEnlistmentDayInputState(true)
        }
    }

    fun updateDDay (_dDay: String) {
        dDay = _dDay
        _dataState.update { currentState ->
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
        _dataState.update { state ->
            state.copy(show = current)
        }
    }
    fun errorState (current: Boolean) {
        _uiState.update { state ->
            state.copy(error = current)
        }
    }

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val today: LocalDate = LocalDate.now()

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

    fun backHomeCountDown (localDate: LocalDate, lastDay: String): Long {
        val _lastDay = LocalDate.parse(lastDay, formatter)
        return ChronoUnit.DAYS.between(localDate, _lastDay)
    } // 計算還剩幾天

    fun onDateSubmit () {
        if (_dataState.value.birthdaySelect != "" && _dataState.value.enlistmentDaySelect != ""
            &&_dataState.value.dDay != "") {
            errorState(false)
            val lastDaysDate = calculateWhichDay2Leave(
                _dataState.value.birthdaySelect,
                _dataState.value.enlistmentDaySelect,
                _dataState.value.dDay
            )
            _dataState.update { value ->
                value.copy(day2Leave = lastDaysDate)
            }
            showDetail(true)
            val dayCountDown = backHomeCountDown(today, lastDaysDate)
            _uiState.update { value ->
                value.copy(backHomeCountDown = dayCountDown)
            }
            saveData(
                id = dataState.value.id,
                birthdaySelect = dataState.value.birthdaySelect,
                enlistmentDaySelect = dataState.value.enlistmentDaySelect,
                dDay = dataState.value.dDay,
                day2Leave = dataState.value.day2Leave,
                show = dataState.value.show,
            )

        } else {
            showDetail(false)
            errorState(true)
        }
    }
}