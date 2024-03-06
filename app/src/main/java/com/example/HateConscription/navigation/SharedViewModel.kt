package com.example.HateConscription.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    private val _sharedState = MutableStateFlow(mutableMapOf<String, String>() )
    val sharedState: StateFlow<MutableMap<String, String>> = _sharedState.asStateFlow()
    fun updateEDay (enterDay: String) {
        _sharedState.value.put("enlistmentDay", enterDay)
    }
    fun updateLDay (lastDay: String) {
        _sharedState.value.put("lastDay", lastDay)
    }
}