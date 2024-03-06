package com.example.HateConscription.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.HateConscription.R
import com.example.HateConscription.navigation.SharedViewModel

@ExperimentalMaterial3Api
@Composable
fun DatePickerScreen (
    dateNDayViewModel: DateNDaysViewModel,
    sharedViewModel: SharedViewModel,
) {

    val dateUiState by dateNDayViewModel.uiState.collectAsStateWithLifecycle()
    val dataState by dateNDayViewModel.dataState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.padding(16.dp, 16.dp)
    ) { currentPadding ->

        Column (
            modifier = Modifier.padding(currentPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.Saying),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )
            if (!dataState.saved) {
                CustomDatePicker (
                    stringResource(id = R.string.birthday),
                    onInputDateChanged = { dateNDayViewModel.updateBirthdayInput(it) },
                    isIllegalInput = dateUiState.isIllegalBirthdayDate,
                )
                CustomDatePicker(
                    stringResource(id = R.string.Enlistment_date),
                    onInputDateChanged = {
                        dateNDayViewModel.updateEnlistmentDay(it)
                    },
                    isIllegalInput = dateUiState.isIllegalEnlistmentDate
                )
            } else {
                ShowSelectedDates(
                    birthday = dataState.birthdaySelect,
                    enlistmentDate = dataState.enlistmentDaySelect
                )
            }

            DaysInputField(
                inputDate = dateNDayViewModel.dDay,
                onInputDateChanged = { dateNDayViewModel.updateDDay(it) }
            )
            if (dataState.show) {
                ShowDaysContent(
                    dateDetails = dataState.day2Leave,
                    daysDetail =  dateNDayViewModel.backHomeCountDown(
                    dateNDayViewModel.today,
                    dataState.day2Leave
                    )
                )
                sharedViewModel.updateLDay(dataState.day2Leave)
                sharedViewModel.updateEDay(dataState.enlistmentDaySelect)
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ){
                OutlinedButton(
                    onClick = {
                        dateNDayViewModel.saved(false)
                    },
                    modifier = Modifier.padding(16.dp),
                    enabled = true
                ) {
                    Text(
                        text = stringResource(id = R.string.edit),
                        fontSize = 16.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        dateNDayViewModel.onDateSubmit()
                    },
                    modifier = Modifier.padding(16.dp),
                    enabled = true
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        fontSize = 16.sp
                    )
                }


            }

            if (dateUiState.error) {
                LaunchedEffect(snackbarHostState) {
                    val result = snackbarHostState.showSnackbar(
                        "請填入所有空格",
                        actionLabel = "OK",
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            dateNDayViewModel.errorState(false)
                        }
                        SnackbarResult.Dismissed -> {
                            dateNDayViewModel.errorState(false)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSelectedDates(
    birthday: String,
    enlistmentDate: String
) {
    Text(
        text = "生日${birthday}",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )

    Text(
        text = "入伍日${enlistmentDate}",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
fun ShowDaysContent(
    dateDetails: String,
    daysDetail: Long
) {
    Text(
        text = "退伍日${dateDetails}",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )

    Text(
        text = "還有${daysDetail}天退伍",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
fun DaysInputField (
    inputDate: String,
    onInputDateChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = inputDate,
        onValueChange = onInputDateChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.Discount_Days),
            style = TextStyle(fontSize = 18.sp)) },
        singleLine = true,
        placeholder = { Text(text = "N天", style = TextStyle(color = Color.LightGray))},
        supportingText = { Text(text = "N天", style = TextStyle(fontSize = 18.sp))},
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}
