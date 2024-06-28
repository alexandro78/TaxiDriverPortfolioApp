package com.taxicardriver.views.screens.ridersearchscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabdriver.R
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer


@Composable
fun RiderRateScreen() {
    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("Оцініть райдера")
        }
    }

    MainScaffoldContainer(
        headerText,
        bodyContent = { RiderRateContentSection() },
        bottomBar = {
            RiderRateConfirmationButton()
        },
        bottomBarcontainerColor = Color(0xFF5F5C68),
        bottomBarHeight = 100
    )
}


@Composable
fun RiderRateContentSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5F5C68))
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp, 50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFBCED09))
            )
            {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){

                    Text(
                        text = "Готівка",
                        color = Color(0xFFFFFFFF),
                        fontSize = 28.sp,
                        modifier = Modifier.shadow(40.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (modifier = Modifier.fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp),
            horizontalArrangement = Arrangement.Center){
            Text(
                text = "Візьміть з райдера",
                color = Color(0xFFADADB6),
                fontSize = 20.sp,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp, 50.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){

                    Text(
                        text = "100",
                        color = Color(0xFF26A69A),
                        fontSize = 28.sp,
                        modifier = Modifier,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ukraine_hryvnia_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .offset(y = (-1).dp),
                        tint = Color(0xFFBCED09)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF414145))
                .height(270.dp)
                .padding(start = 8.dp, top = 14.dp, bottom = 14.dp, end = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF414145))
                .border(1.dp, Color(0xFF247BA0), RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-5).dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var pickStarRate by remember { mutableIntStateOf(0) }
                (0 until 5).forEach { i ->
                    IconButton(modifier = Modifier.size(70.dp),
                        onClick = {
                            pickStarRate = i+1
                        }) {
                        if (i < pickStarRate) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.star_soft_corner),
                                contentDescription = "Localized description",
                                tint = Color(0xFFFFA726),
                                modifier = Modifier
                                    .size(60.dp),
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.star_soft_corner),
                                contentDescription = "Localized description",
                                tint = Color(0xFF726F6B),
                                modifier = Modifier
                                    .size(60.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RiderRateConfirmationButton() {
//    val mainViewModel: MainViewModel = viewModel()
    val navController = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-4).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxWidth()
            .height(70.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color(0xFFFE6D73)
            ),
            onClick = {
                navController.navigate("start_page") {
                    popUpTo(route = "start_page") { inclusive = true }
                }
            }) {
            Text(
                "Ok",
                fontSize = 20.sp
            )
        }
    }
}