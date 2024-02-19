package com.example.HateConscription.drinkWaterTable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.HateConscription.R

@Composable
fun DrinkWaterCardScreen (
    drinkWaterViewModel: DrinkWaterViewModel = viewModel()
) {
    val drinkWaterUiState by drinkWaterViewModel.uiState.collectAsStateWithLifecycle()
    LazyColumn {
        item(12) {
            DrinkWaterCard(
                inputText = drinkWaterViewModel.userInput,
                onInputChanged = { drinkWaterViewModel.updateFormDate(it) })
        }
    }

}

@Composable
fun DrinkWaterCard (
    inputText: String,
    onInputChanged: (String) -> Unit
) {
    ElevatedCard (
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(text = inputText, onChange = onInputChanged)
    }
}

@Composable
fun CardContent (
    text: String,
    onChange: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
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
            Text(text = "每日飲水量", style = MaterialTheme.typography.labelMedium)
            Text(text = "日期", style = MaterialTheme.typography.labelSmall)

            if (expanded) {
                Column (modifier = Modifier.padding(16.dp)){
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "體重") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "體溫") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "9:00 飲水量") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "12:00 飲水量") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "15:00 飲水量") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "18:00 飲水量") })
                    OutlinedTextField(value = text, onValueChange = onChange, label = { Text(text = "總飲水量") })
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