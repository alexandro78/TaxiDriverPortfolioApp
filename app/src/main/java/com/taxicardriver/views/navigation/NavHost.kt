package com.taxicardriver.views.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taxicardriver.views.screens.HomeScreen
import com.taxicardriver.views.screens.authorizationscreen.UserAuthScreen
import com.taxicardriver.views.screens.registeruserscreen.RegisterUserScreen
import com.taxicardriver.views.screens.ridersearchscreens.AutoRideOrderSuggestionScreen
import com.taxicardriver.views.screens.ridersearchscreens.CurrentOrderScreen
import com.taxicardriver.views.screens.ridersearchscreens.DispatchModeScreen
import com.taxicardriver.views.screens.ridersearchscreens.RiderRateScreen
import com.taxicardriver.views.screens.settings.VehicleScreen

val LocalNavController = compositionLocalOf<NavController> { error("No NavController provided") }
@Composable
fun Navigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        NavHost(navController = navController, startDestination = "start_page") {
            composable("start_page") {
                HomeScreen()
            }
            composable("dispatch_mode_screen") {
                DispatchModeScreen()
            }

            composable("current_order_screen") {
                CurrentOrderScreen()
            }

            composable("auto_ride_order_suggestion_screen") {
                AutoRideOrderSuggestionScreen()
            }

            composable("rider_rate_screen") {
                RiderRateScreen()
            }

            composable("vehicle_screen") {
                VehicleScreen()
            }

            composable("register_user_screen") {
                RegisterUserScreen()
            }

            composable("auth_user_screen") {
                UserAuthScreen()
            }
        }
    }
}