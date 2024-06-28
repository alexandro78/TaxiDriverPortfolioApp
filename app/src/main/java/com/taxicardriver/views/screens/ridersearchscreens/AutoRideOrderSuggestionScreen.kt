package com.taxicardriver.views.screens.ridersearchscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabdriver.R
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer
import com.taxicardriver.views.servicecomponents.ConfirmationSwipeButtonComponent
import com.taxicardriver.views.servicecomponents.LinearProgressIndicator
import kotlinx.coroutines.delay


@Composable
fun AutoRideOrderSuggestionScreen() {
    val navController = LocalNavController.current
    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("Вхідне замовлення")
        }
    }

    var acceptTimer by remember { mutableIntStateOf(20) }
    if(acceptTimer == 0) {
        navController.navigate("start_page")
    }

    MainScaffoldContainer(
        headerText,
        bodyContent = { AutoOrderSuggestionContentSection(acceptTimer = acceptTimer) },
        bottomBar = {
            Column {
                        Row (modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically){
            LinearProgressIndicator(
                acceptTimer = acceptTimer
            )
        }
                AutoOrderConfirmationButton(acceptTimer = acceptTimer) { newTimerValue ->
                    acceptTimer = newTimerValue
                }
            }
        },
        bottomBarcontainerColor = Color(0xFF1A1B1A),
        bottomBarHeight = 120
    )
}


@Composable
fun CurrentOrderItem(acceptTimer: Int) {
    val navController = LocalNavController.current
    val backgroundColorMeetOptionIcon = Brush.horizontalGradient(
        colors = listOf(Color(0xFF32C4C0), Color(0xFF008B8B))
    )

    val backgroundColorMeetOption = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF32C4C0).copy(alpha = 0.3f),
            Color(0xFF020202).copy(alpha = 0.3f)
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF161618))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .background(Color(0xFF247BA0).copy(alpha = 0.6f))
                .padding(start = 2.dp, end = 10.dp)
        ) {
            Text(
                text = "Rider: Name",
                fontSize = 16.sp,
                color = Color(0xFFF5F5F5),
                modifier = Modifier.padding(12.dp)
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(end = 14.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.grade_star_icon),
                        contentDescription = "Localized description",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier
                            .size(28.dp),
                    )

                    Text(
                        text = "5.0",
                        color = Color(0xFFECECEE),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )


                    Box(
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFF00C4E2), Color(0xFF00C4E2))
                                )
                            )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.trip_number),
                            contentDescription = "Localized description",
                            tint = Color(0xFFD2F0F8),
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Center),
                        )
                    }

                    Text(
                        text = "115",
                        color = Color(0xFFECECEE),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .background(Color(0xFF247BA0).copy(alpha = 0.2f)),
//                .padding(start = 2.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Тополь",
                color = Color(0xFFECECEE),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .padding(8.dp)
            )


            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Localized description",
                tint = Color(0xFF66BB6A),
                modifier = Modifier
                    .padding(5.dp)
                    .size(18.dp),
            )

            Text(
                text = "Тополь 2",
                color = Color(0xFFECECEE),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 18.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "15 грн/км",
                    color = Color(0xFFECECEE),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

        LocationSetSection()

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sportcar_svg_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 5.dp)
                        .size(40.dp),
                    tint = Color(0xFFC99D42)
                )

                Text(
                    text = "Lux",
                    color = Color(0xFFECECEE),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(end = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "3.5 км",
                    color = Color(0xFFBCED09),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(65.dp, 30.dp)
                        .background(
                            Color(0xFFBCED09),
                            shape = RoundedCornerShape(4.dp),
                        )
                ) {
                    Text(
                        text = "150 грн",
                        color = Color(0xFF131312),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, end = 16.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(modifier = Modifier.size(180.dp, 46.dp),
                border = BorderStroke(1.dp, Color(0xFFEF5350)),
                onClick = {  navController.popBackStack() }) {
                Text(
                    "Відмовитись",
                    color = Color(0xFFDDD2D2),
                    fontSize = 16.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(backgroundColorMeetOption)
        ) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 15.dp)) {
                Text(
                    text = "Активовано опцію:",
                    color = Color(0xFFE7E7E3),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "option name",
                    color = Color(0xFFE7E7E3),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, top = 6.dp, end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(backgroundColorMeetOptionIcon)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.handshake_chat_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(34.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1B1A))
                .padding(start = 14.dp, top = 10.dp, end = 10.dp),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Коментар: some text here....",
                color = Color(0xFFD1C6C6),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun LocationSetSection() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(start = 6.dp, end = 10.dp)
                .clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Точка А kdjkfjkdjfkdjkfkdjkj jkdjkjfkdjk",
                color = Color(0xFFECECEE),
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(0.9f)
                    .padding(start = 8.dp)
            )

            Row(
                modifier = Modifier.weight(0.1f),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.navigation_locate_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(40f)
                        .offset(y = (-4).dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(start = 6.dp, end = 10.dp)
                .clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Точка B kdjkfjkdjfkdjkfkdjkj jkdjkjfkdjk",
                color = Color(0xFFECECEE),
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(0.9f)
                    .padding(start = 8.dp)
            )

            Row(
                modifier = Modifier.weight(0.1f),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.navigation_locate_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(40f)
                        .offset(y = (-4).dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(start = 6.dp, end = 10.dp)
                .clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Точка C kdjkfjkdjfkdjkfkdjkj jkdjkjfkdjk",
                color = Color(0xFFECECEE),
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(0.9f)
                    .padding(start = 8.dp)
            )

            Row(
                modifier = Modifier.weight(0.1f),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.navigation_locate_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(40f)
                        .offset(y = (-4).dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun AutoOrderSuggestionContentSection(acceptTimer: Int) {
    CurrentOrderItem(acceptTimer = acceptTimer)
}

@Composable
fun AutoOrderConfirmationButton(acceptTimer: Int, onTimerChange: (Int) -> Unit) {
    val navController = LocalNavController.current
    LaunchedEffect(acceptTimer) {
        while (acceptTimer > 0) {
            delay(1000)
            onTimerChange(acceptTimer - 1)
        }
    }
    val backgroundColorConfirmationSwipeButton = Brush.horizontalGradient(
        colors = listOf(Color(0xFFF4F269), Color(0xFF5CB270))
    )
    Row(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-4).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConfirmationSwipeButtonComponent(text = "Прийняти",
            backgroundColorContainer = backgroundColorConfirmationSwipeButton,
            additionalTextStyle = TextStyle(Color(0xFF171817)),
            mainTextStyle = TextStyle(
                Color(0xFF252726),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            iconColor = Color(0xFF171817),
            timer = if (acceptTimer != 0) {
                "$acceptTimer"
            } else {
                null
            },
            onSwipe = {
//                navController.popBackStack()
                navController.navigate("current_order_screen")
            })
    }
}