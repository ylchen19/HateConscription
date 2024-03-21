package com.example.HateConscription.drinkWaterTable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.HateConscription.navigation.SharedViewModel

@Composable
fun DrinkWaterCardScreen (
    drinkWaterViewModel: DrinkWaterViewModel,
    sharedViewModel: SharedViewModel,
    navigationToEdit: (String) -> Unit,
) {
    val sharedState by sharedViewModel.sharedState.collectAsStateWithLifecycle()
    val dateList = drinkWaterViewModel.getPerDay(
        start = sharedState["enlistmentDay"],
        end = sharedState["lastDay"]
    ).toMutableStateList()
    DrinkWaterList(
        dateList = dateList,
        onClicked = navigationToEdit
    )
}

@Composable
fun DrinkWaterList (
    dateList: SnapshotStateList<String>,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(dateList) {date ->
            DrinkWaterCard(
                date = date,
                modifier = modifier.clickable { onClicked(date) }
            )
        }
    }
}

@Composable
fun DrinkWaterCard (
    date: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard (
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Row(
            modifier = modifier
                .padding(12.dp)
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "每日飲水量", style = MaterialTheme.typography.headlineSmall)
                Text(text = "日期:${date}", style = MaterialTheme.typography.labelLarge)


            }
        }
    }
}

