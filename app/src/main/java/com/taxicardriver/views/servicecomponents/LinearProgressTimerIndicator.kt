package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun LinearProgressIndicator(
    acceptTimer: Int
) {
    val totalSteps = 500
    val delayMillis = 20000 / totalSteps
    val animatedProgress = remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        repeat(totalSteps) {
            delay(delayMillis.toLong())
            animatedProgress.value -= 1f / totalSteps
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.Center),
            color = Color(0xFFB084CC),
            trackColor = Color(0xFFFE6D73),
        )

        Text(
            text = "$acceptTimer",
            modifier = Modifier
                .align(Alignment.Center),
            color = Color(0xFF000000),
            fontWeight = FontWeight.W500
        )
    }
}


@Composable
fun TimerButton(acceptTimer: Int) {
    OutlinedButton(modifier = Modifier
        .fillMaxWidth(0.9f),
        border = BorderStroke(0.4.dp, Color(0xFF9C27B0)),
        onClick = { /*TODO*/ }) {
        Text(text = "Прийняти $acceptTimer",
            modifier = Modifier
                .padding(12.dp),
            color = Color.White)
    }
}
