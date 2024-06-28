package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.views.navigation.LocalNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeActiveBottomBar() {
    val scope = CoroutineScope(Dispatchers.Main)
    val navController = LocalNavController.current
    val mainViewModel: MainViewModel = viewModel()
//    var acceptTimer by remember { mutableIntStateOf(40) }

    val backgroundActiveDispatcherTabHighlight = if (mainViewModel.dispatcherModScreenIsActive) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF32C4C0).copy(alpha = 0.3f),
                Color(0xFF020202).copy(alpha = 0.3f)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent,
            )
        )
    }

    val backgroundActiveOrdersTabHighlight = if (mainViewModel.orderModScreenIsActive) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF32C4C0).copy(alpha = 0.3f),
                Color(0xFF020202).copy(alpha = 0.3f)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent,
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020202))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .background(backgroundActiveDispatcherTabHighlight)
                .height(70.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {
                    mainViewModel.dispatcherModScreenIsActive = true
                    mainViewModel.orderModScreenIsActive = false
                    scope.launch {
                        delay(1000)
                        mainViewModel.dispatcherModScreenIsActive = false
                    }
                    navController.navigate("dispatch_mode_screen")
                }) {
                Text(
                    text = "Ефір",
                    color = Color(0xFFECECEE),
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        VerticalDivider(modifier = Modifier.height(80.dp),
            color = Color(0xFFECECEE))

        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .background(backgroundActiveOrdersTabHighlight)
                .height(70.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {
                    mainViewModel.orderModScreenIsActive = true
                    mainViewModel.dispatcherModScreenIsActive = false
                    scope.launch {
                        delay(1000)
                        mainViewModel.orderModScreenIsActive = false
                    }
                    navController.navigate("current_order_screen")
                }) {
                Text(
                    text = "Замовлення",
                    modifier = Modifier,
                    color = Color(0xFFECECEE),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}