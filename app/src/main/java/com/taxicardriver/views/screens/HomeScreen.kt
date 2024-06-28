package com.taxicardriver.views.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.viewmodels.HomeScreenViewModel
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.servicecomponents.HomeActiveBottomBar
import com.taxicardriver.views.servicecomponents.HomeInactiveBottomBar
import com.taxicardriver.views.servicecomponents.HomeModalNavigationDrawer
import com.taxicardriver.views.servicecomponents.LoadingDialogScreen
import com.taxicardriver.views.servicecomponents.TurnOffOnlineButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val mainViewModel: MainViewModel = viewModel()
    val navController = LocalNavController.current

    val homeScreenViewModel: HomeScreenViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        homeScreenViewModel.loadHomeScreenUserData()
    }

    LaunchedEffect(homeScreenViewModel.responseStatus) {
        when (val status = homeScreenViewModel.responseStatus) {
            true -> {
                homeScreenViewModel.isLoadingScreenDialogVisible = false
                Log.d("LoginJwt", "= состояние true")
                Log.d("LoginJwt", "=" + homeScreenViewModel.responseStatus)
            }

            false -> {
                Log.d("LoginJwt", "= false")
                Log.d(
                    "LoginJwt",
                    "=" + homeScreenViewModel.responseStatus
                )
                homeScreenViewModel.isLoadingScreenDialogVisible = true
                if (!homeScreenViewModel.serverConnectProblem) {
                    navController.navigate("auth_user_screen")
                }
            }

            null -> {
                Log.d("LoginJwt", "= состояние NULL")
                Log.d("LoginJwt", "=" + homeScreenViewModel.responseStatus)
                homeScreenViewModel.isLoadingScreenDialogVisible = true
            }
        }
    }


    if (homeScreenViewModel.isLoadingScreenDialogVisible || homeScreenViewModel.serverConnectProblem) {
        LoadingDialogScreen(
            serverConnectProblem = homeScreenViewModel.serverConnectProblem,
            serverConnectProblemType = homeScreenViewModel.serverConnectProblemType
        )
    }


    if (mainViewModel.isOnlineModActive) {
        LaunchedEffect(key1 = true) {
            delay(5000)
//        navController.graph.startDestinationRoute?.let { navController.popBackStack(it, inclusive = false) }
//        navController.navigate("trip_suggested_screen")
            navController.navigate("auto_ride_order_suggestion_screen") {
                popUpTo(route = "start_page") { inclusive = false }
            }
        }
    }

    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("CARDRIVER")
        }
    }

    HomeModalNavigationDrawer(
        headerText = headerText,
        drawerState = drawerState,
        scope = scope
    )
}


@Composable
fun HomeScreenHeadIconButton(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    IconButton(onClick = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Localized description",
            tint = Color(0xFFBCC5CC)
        )
    }
}


@Composable
fun HomeScreenContentSection() {
    val mainViewModel: MainViewModel = viewModel()
    fun onTurnOffOnlineClick() {
        mainViewModel.isOnlineModActive = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF42A5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (mainViewModel.isOnlineModActive) {
            IndeterminateCircularIndicator()
            TurnOffOnlineButton(onClick = { onTurnOffOnlineClick() })
        }
    }
}


@Composable
fun HomeScreenBottomBarSection() {
    val mainViewModel: MainViewModel = viewModel()
    if (!mainViewModel.isOnlineModActive) {
        HomeInactiveBottomBar()
    } else {
        HomeActiveBottomBar()
    }
}


@Composable
fun IndeterminateCircularIndicator() {
    val mainViewModel: MainViewModel = viewModel()
    if (!mainViewModel.isOnlineModActive) return

    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF61067),
        trackColor = Color(0xFFBCED09),
    )
}