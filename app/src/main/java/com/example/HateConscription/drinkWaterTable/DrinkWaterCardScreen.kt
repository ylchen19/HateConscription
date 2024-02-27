package com.example.HateConscription.drinkWaterTable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.HateConscription.R
import com.example.HateConscription.navigation.SharedViewModel

@Composable
fun DrinkWaterCardScreen (
    drinkWaterViewModel: DrinkWaterViewModel = viewModel(),
    sharedViewModel: SharedViewModel
) {
    val sharedState by sharedViewModel.sharedState.collectAsStateWithLifecycle()
    val dateList = drinkWaterViewModel.getPerDay(
        start = sharedState["enlistmentDay"],
        end = sharedState["lastDay"]
    ).toMutableStateList()

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(dateList) {date ->
            DrinkWaterCard(drinkWaterViewModel = drinkWaterViewModel, date = date)
        }
    }
}

@Composable
fun DrinkWaterCard (
    drinkWaterViewModel: DrinkWaterViewModel,
    date: String
) {
    ElevatedCard (
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(
            date = date,
            onBodyWeightInputChanged = { drinkWaterViewModel.updateBodyWeight(it) },
            onBodyTemperatureInputChanged = { drinkWaterViewModel.updateBodyTemperature(it) },
            onWater1InputChanged = { drinkWaterViewModel.updateWater1(it) },
            onWater2InputChanged = { drinkWaterViewModel.updateWater2(it) },
            onWater3InputChanged = { drinkWaterViewModel.updateWater3(it) },
            onWater4InputChanged = { drinkWaterViewModel.updateWater4(it) },
            onTotalWaterInputChanged = { drinkWaterViewModel.updateWaterTotal(it)},
            onClicked = { drinkWaterViewModel.updateTheForm()}
        )
    }
}

@Composable
fun CardContent (
    date: String,
    inputState: DrinkWaterFormInputState = rememberDrinkWaterFormInputState(""),
    onBodyWeightInputChanged: (String) -> Unit,
    onBodyTemperatureInputChanged: (String) -> Unit,
    onWater1InputChanged: (String) -> Unit,
    onWater2InputChanged: (String) -> Unit,
    onWater3InputChanged: (String) -> Unit,
    onWater4InputChanged: (String) -> Unit,
    onTotalWaterInputChanged: (String) -> Unit,
    onClicked: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var save by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "每日飲水量", style = MaterialTheme.typography.headlineSmall)
            Text(text = "日期:${date}", style = MaterialTheme.typography.labelLarge)

            if (expanded) {
                Column (modifier = Modifier.padding(16.dp)){
                    FormInputField(
                        input = inputState.bodyWeight,
                        onInputChanged = {
                            onBodyWeightInputChanged(inputState.updateBodyWeight(it))
                        },
                        label = "體重"
                    )
                    FormInputField(
                        input = inputState.bodyTemp,
                        onInputChanged = {
                            onBodyTemperatureInputChanged(inputState.updateBodyTemperature(it))
                        },
                        label = "體溫"
                    )
                    FormInputField(
                        input = inputState._water1,
                        onInputChanged = {
                            onWater1InputChanged(inputState.updateWater1(it))
                        },
                        label = "9:00 飲水量"
                    )
                    FormInputField(
                        input = inputState._water2,
                        onInputChanged = {
                            onWater2InputChanged(inputState.updateWater2(it))
                        },
                        label = "12:00 飲水量"
                    )
                    FormInputField(
                        input = inputState._water3,
                        onInputChanged = {
                            onWater3InputChanged(inputState.updateWater3(it))
                        },
                        label = "15:00 飲水量"
                    )
                    FormInputField(
                        input = inputState._water4,
                        onInputChanged = {
                            onWater4InputChanged(inputState.updateWater4(it))
                        },
                        label = "18:00 飲水量"
                    )
                    FormInputField(
                        input = inputState._totalWater,
                        onInputChanged = {
                            onTotalWaterInputChanged(inputState.updateWaterTotal(it))
                        },
                        label = "總飲水量"
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        ElevatedButton(
                            onClick = {
                                save = !save
                                onClicked()
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
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
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

class DrinkWaterFormInputState(
    bodyWeight: String,
    bodyTemperature: String,
    water1: String,
    water2: String,
    water3: String,
    water4: String,
    totalWater: String
) {
    var bodyWeight by mutableStateOf(bodyWeight)
        private set

    var bodyTemp by mutableStateOf(bodyTemperature)
        private set

    var _water1 by mutableStateOf(water1)
        private set

    var _water2 by mutableStateOf(water2)
        private set

    var _water3 by mutableStateOf(water3)
        private set

    var _water4 by mutableStateOf(water4)
        private set

    var _totalWater by mutableStateOf(totalWater)
        private set

    fun updateBodyWeight (weight: String): String {
        bodyWeight = weight
        return bodyWeight
    }

    fun updateBodyTemperature (temp: String): String {
        bodyTemp = temp
        return bodyTemp
    }

    fun updateWater1 (water1: String): String {
        _water1 = water1
        return _water1
    }

    fun updateWater2 (water2: String): String {
        _water2 = water2
        return _water2
    }

    fun updateWater3 (water3: String): String {
        _water3 = water3
        return _water3
    }

    fun updateWater4 (water4: String): String {
        _water4 = water4
        return _water4
    }

    fun updateWaterTotal (totalWater: String): String {
        _totalWater = totalWater
        return totalWater
    }

    companion object {
        val Saver: Saver<DrinkWaterFormInputState, *> = listSaver(
            save = { listOf(
                it.bodyWeight,
                it.bodyTemp,
                it._water1,
                it._water2,
                it._water3,
                it._water4,
                it._totalWater) },
            restore = {
                DrinkWaterFormInputState(
                    bodyWeight = it[0],
                    bodyTemperature = it[1],
                    water1 = it[2],
                    water2 = it[3],
                    water3 = it[4],
                    water4 = it[5],
                    totalWater = it[6]
                )
            }
        )
    }
}

@Composable
fun rememberDrinkWaterFormInputState (input: String): DrinkWaterFormInputState =
    rememberSaveable(input, saver = DrinkWaterFormInputState.Saver) {
        DrinkWaterFormInputState(
            bodyWeight = input,
            bodyTemperature = input,
            water1 = input,
            water2 = input,
            water3 = input,
            water4 = input,
            totalWater = input
        )
    }
