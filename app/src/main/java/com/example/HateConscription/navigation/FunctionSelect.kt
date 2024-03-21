package com.example.HateConscription.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.HateConscription.calendar.DateNDaysViewModel
import com.example.HateConscription.calendar.DatePickerScreen
import com.example.HateConscription.drinkWaterTable.DrinkWaterCardScreen
import com.example.HateConscription.drinkWaterTable.DrinkWaterViewModel
import com.example.HateConscription.drinkWaterTable.drinkWaterCard.CardContentScreen
import com.example.HateConscription.drinkWaterTable.drinkWaterCard.CardContentViewModel

data class BottomNavigationItem (
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String
)

enum class Screens {
    DatePickerScreen,
    DrinkWaterScreen,
    CardEditScreen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar (sharedViewModel: SharedViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination
                val selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val items = listOf(
                    BottomNavigationItem(
                        title = "退伍日計算",
                        selectedIcon = Icons.Filled.DateRange,
                        unSelectedIcon = Icons.Outlined.DateRange,
                        route = Screens.DatePickerScreen.name
                    ),
                    BottomNavigationItem(
                        title = "飲水小卡",
                        selectedIcon = Icons.Filled.Add,
                        unSelectedIcon = Icons.Outlined.Add,
                        route = Screens.DrinkWaterScreen.name
                    )
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) }
                    )
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.DatePickerScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Screens.DatePickerScreen.name
            ) {backStackEntry ->
                DatePickerScreen(
                    dateNDayViewModel = hiltViewModel<DateNDaysViewModel>(backStackEntry),
                    sharedViewModel = sharedViewModel
                )
            }
            composable(
                route = Screens.DrinkWaterScreen.name
            ) {backStackEntry ->
                DrinkWaterCardScreen(
                    drinkWaterViewModel = hiltViewModel<DrinkWaterViewModel>(backStackEntry),
                    sharedViewModel = sharedViewModel,
                    navigationToEdit = { date ->
                        navController.navigate(route = Screens.CardEditScreen.name + "?date=$date")
                    },
                )
            }
            composable(
                route = Screens.CardEditScreen.name + "?date={date}",
                arguments = listOf(navArgument("date") {
                    type = NavType.StringType
                })
            ) {backStackEntry ->
                backStackEntry.arguments!!.getString("date")!!.let {
                    CardContentScreen(
                        navigateBack = { navController.popBackStack() },
                        viewModel = hiltViewModel<CardContentViewModel>(backStackEntry),
                        date = it
                    )
                }

            }
        }
    }
}