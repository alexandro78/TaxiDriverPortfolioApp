package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TurnOffOnlineButton(onClick: () -> Unit) {
    FloatingActionButton(modifier = Modifier.padding(top = 10.dp)
        .size(180.dp, 38.dp),
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFF247BA0),
        onClick = { onClick() }) {
            Text("Вийти offline",
                color = Color(0xFFBCED09),
                fontSize = 18.sp,
                modifier = Modifier.shadow(22.dp)
            )
    }
}