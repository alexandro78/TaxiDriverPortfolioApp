package com.taxicardriver.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var isOnlineModActive by mutableStateOf(false)
    var dispatcherModScreenIsActive by mutableStateOf(false)

    var orderModScreenIsActive by mutableStateOf(false)

    var currentActiveOrderScreen by mutableStateOf(true)
    var orderHistoryActiveScreen by mutableStateOf(false)

    var currentOrderModeisEmpty by mutableStateOf(false)
    var historyModeisEmpty by mutableStateOf(false)

    /////Vehicle screen//////
    var activeVehicleTab by mutableStateOf(true)
    var activeVehicleItemId by mutableIntStateOf(0)
}