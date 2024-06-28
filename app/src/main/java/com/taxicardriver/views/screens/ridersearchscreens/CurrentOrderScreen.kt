package com.taxicardriver.views.screens.ridersearchscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cabdriver.R
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer
import com.taxicardriver.views.servicecomponents.ConfirmationSwipeButtonComponent


@Composable
fun CurrentOrderScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val bottomBarHeight =  if (mainViewModel.currentActiveOrderScreen && !mainViewModel.currentOrderModeisEmpty) {
        100
    } else {
        0
    }

    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("Замовлення")
        }
    }

    MainScaffoldContainer(
        headerText,
        navIcon = { OrderModeScreenHeadIconButton() },
        bodyContent = { OrderModeScreenContentSection() },
        bottomBar = {
            if (mainViewModel.currentActiveOrderScreen && !mainViewModel.currentOrderModeisEmpty) {
                OrderModeScreenButton()
            }
        },
        bottomBarcontainerColor = Color(0xFF1A1B1A),
        bottomBarHeight = bottomBarHeight
    )
}


@Composable
fun OrderModeScreenHeadIconButton() {
    val navController = LocalNavController.current
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Localized description",
            tint = Color(0xFFB1B0B6),
            modifier = Modifier
                .size(20.dp)
        )
    }
}


@Composable
fun OrderModeScreenContentSection() {
    val mainViewModel: MainViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1B1A))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFF1A1B1A))
                .padding(start = 2.dp, end = 2.dp)
                .drawWithContent {
                    if (mainViewModel.currentActiveOrderScreen) {
                        drawLine(
                            color = Color(0xFF17C3B2),
                            start = Offset(0f, size.height),
                            end = Offset(size.width / 2, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }

                    if (mainViewModel.orderHistoryActiveScreen) {
                        drawLine(
                            color = Color(0xFF17C3B2),
                            start = Offset(size.width / 2, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                    drawContent()
                },
        ) {
            TextButton(
                onClick = {
                    mainViewModel.currentActiveOrderScreen = true
                    mainViewModel.orderHistoryActiveScreen = false
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(2.dp),
            ) {
                Text(
                    "Активні",
                    color = Color(0xFFBCED09),
                    fontSize = 20.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = {
                    mainViewModel.orderHistoryActiveScreen = true
                    mainViewModel.currentActiveOrderScreen = false
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(2.dp),
            ) {
                Text(
                    "Історія",
                    color = Color(0xFFBCED09),
                    fontSize = 20.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (mainViewModel.currentActiveOrderScreen) {
            if (mainViewModel.currentOrderModeisEmpty) {
                EmptyModeScreen("Активних замовлень немає")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp),
                    content = {
                        items(
                            listOf(
                                "1111111"
                            )
                        ) {
                            CurrentOrderItem()
                        }
                    })
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF5F5C68))
            ) {
                if (mainViewModel.historyModeisEmpty) {
                    EmptyModeScreen("Історія поки що порожня")
                } else {
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(2.dp),
                        content = {
                            items(
                                listOf(
                                    "1111111",
                                    "1111111",
                                    "1111111",
                                    "1111111",
                                    "1111111",
                                    "1111111",
                                )
                            ) {
                                HistoryOrderItem()
                            }
                        })
                }
            }
        }
    }
}


@Composable
fun EmptyModeScreen(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = (-30).dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFD6D4DD),
                fontSize = 26.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun CurrentOrderItem() {
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
            .fillMaxWidth()
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF2D2F2E))
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFF247BA0), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                shape = RoundedCornerShape(2.dp),
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.call_filled_icon),
                    contentDescription = "Add",
                    modifier = Modifier
                        .offset(x = (-5).dp, y = 1.dp)
                        .size(24.dp),
                    tint = Color(0xFFFFFDD0)
                )

                Text(
                    "Зв'язатися",
                    color = Color(0xFFDFDAD2),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Center
                )

            }

            Spacer(modifier = Modifier.width(20.dp))
            TextButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFF247BA0), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                shape = RoundedCornerShape(2.dp),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.fliped_list_icon),
                        contentDescription = "Add",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 6.dp)
                            .size(24.dp),
                        tint = Color(0xFFFFFDD0)
                    )

                    Text(
                        "Дії",
                        color = Color(0xFFDFDAD2),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFF7068CF), RoundedCornerShape(14.dp))
                    .padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = "Подача авто",
                    color = Color(0xFFECECEE),
                    fontSize = 16.sp
                )
            }
        }

        LocationPointSetSection()

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

        Spacer(modifier = Modifier.height(8.dp))
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
                .fillMaxSize()
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
fun LocationPointSetSection() {
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
fun OrderModeScreenButton() {
    val navController = LocalNavController.current
    val backgroundColorConfirmationSwipeButton = Brush.horizontalGradient(
        colors = listOf(Color(0xFF099773), Color(0xFF43B692))
    )

    ConfirmationSwipeButtonComponent(text = "На місті",
        backgroundColorContainer = backgroundColorConfirmationSwipeButton,
        additionalTextStyle = TextStyle(Color(0xFF171817)),
        mainTextStyle = TextStyle(
            Color(0xFF252726),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        iconColor = Color(0xFF171817),
        onSwipe = {
            navController.navigate("rider_rate_screen")
            // Handle swipe action
//                navController.popBackStack()
//                navController.navigate("current_trip_screen")
        })
}
