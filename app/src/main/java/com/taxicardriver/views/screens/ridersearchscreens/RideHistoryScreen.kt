package com.taxicardriver.views.screens.ridersearchscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabdriver.R

@Composable
fun HistoryOrderItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp)
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
                pressedElevation = 5.dp
            ),
            colors = CardColors(
                containerColor = Color(0xFF2E2E31),
                contentColor = Color(0xFF2E2E31),
                disabledContainerColor = Color(0xFF2E2E31),
                disabledContentColor = Color(0xFF2E2E31),
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "15 березня 2024 11:05",
                    color = Color(0xFFD6D6DF),
                    fontSize = 18.sp
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 6.dp, end = 8.dp, bottom = 10.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.location_pin_icon),
                        contentDescription = "Localized description",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(22.dp),
                    )

                    Text(
                        text = "Location adress point 1",
                        color = Color(0xFFD6D6DF),
                        fontSize = 18.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 6.dp, end = 8.dp, bottom = 10.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.location_pin_icon),
                        contentDescription = "Localized description",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(22.dp),
                    )

                    Text(
                        text = "Location adress point 1",
                        color = Color(0xFFD6D6DF),
                        fontSize = 18.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 10.dp, end = 8.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.hryvnia_svg_icon),
                    contentDescription = "Localized description",
                    tint = Color(0xFF26A69A),
                    modifier = Modifier
                        .padding(top = 4.dp, end = 10.dp)
                        .size(36.dp),
                )

                Text(
                    text = "150",
                    color = Color(0xFF9CCC65),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )


                Box(modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp, 32.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(Color(0xFFBF4E3A))
                ){
                    Text(
                        text = "Скасовано",
                        color = Color(0xFFFFFFFF),
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}