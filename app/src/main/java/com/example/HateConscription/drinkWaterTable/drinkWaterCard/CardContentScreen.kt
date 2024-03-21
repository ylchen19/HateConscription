package com.example.HateConscription.drinkWaterTable.drinkWaterCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.HateConscription.R
import com.example.HateConscription.drinkWaterData.DrinkWaterDataState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardContentScreen (
    navigateBack: () -> Unit,
    viewModel: CardContentViewModel,
    date: String
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "$date 飲水紀錄" )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) {
        CardContent(
            date = date,
            inputState = viewModel.inputState.content,
            onInputChanged = viewModel::updateTheForm,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.inputState.isEntryValid) {
                        viewModel.updateDailyRecords()
                    }
                    viewModel.saveDailyRecords()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(it)
        )
    }

}

@Composable
fun CardContent (
    date: String,
    inputState: DrinkWaterDataState,
    onInputChanged: (DrinkWaterDataState) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier
    ) {
    Column(
        modifier = modifier
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "每日飲水量", style = MaterialTheme.typography.headlineSmall)
        Text(text = "日期:$date", style = MaterialTheme.typography.labelLarge)
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            FormInputField(
                input = inputState.bodyWeight,
                onInputChanged = {
                    onInputChanged(inputState.copy(bodyWeight = it))
                },
                label = "體重"
            )
            FormInputField(
                input = inputState.bodyTemperature,
                onInputChanged = {
                    onInputChanged(inputState.copy(bodyTemperature = it))
                },
                label = "體溫"
            )
            FormInputField(
                input = inputState.water1,
                onInputChanged = {
                    onInputChanged(inputState.copy(water1 = it))
                },
                label = "9:00 飲水量"
            )
            FormInputField(
                input = inputState.water2,
                onInputChanged = {
                    onInputChanged(inputState.copy(water2 = it))
                },
                label = "12:00 飲水量"
            )
            FormInputField(
                input = inputState.water3,
                onInputChanged = {
                    onInputChanged(inputState.copy(water3 = it))
                },
                label = "15:00 飲水量"
            )
            FormInputField(
                input = inputState.water4,
                onInputChanged = {
                    onInputChanged(inputState.copy(water4 = it))
                },
                label = "18:00 飲水量"
            )
            FormInputField(
                input = inputState.totalWater,
                onInputChanged = {
                    onInputChanged(inputState.copy(totalWater = it))
                },
                label = "總飲水量"
            )
            Row (
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ElevatedButton(
                    onClick = {
                        onSaveClick()
                        onInputChanged(inputState.copy(dateStamped = date))
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.submit),
                        fontSize = 16.sp
                    )
                }

            }
        }
    }
}

@Composable
fun FormInputField (
    input: String,
    onInputChanged: (String) -> Unit,
    label: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = input,
        onValueChange = onInputChanged,
        label = {
            Text(text = label, style = TextStyle(fontSize = 18.sp))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}