package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cabdriver.R
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.viewmodels.HomeScreenViewModel

@Composable
fun HomeInactiveBottomBar() {
    val homeScreenViewModel: HomeScreenViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    fun onTurnOnOnlineClick() {
        mainViewModel.isOnlineModActive = true
    }

    val backgroundColorConfirmationSwipeButton = Brush.horizontalGradient(
        colors = listOf(Color(0xFF099773), Color(0xFF43B692))
    )

    val backgroundColorInactiveDOption = Brush.horizontalGradient(
        colors = listOf(Color(0xFF8399A2), Color(0xFFC0C0C0))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        ConfirmationSwipeButtonComponent(text = "Увімкнути online",
            backgroundColorContainer = backgroundColorConfirmationSwipeButton,
            onSwipe = {
                onTurnOnOnlineClick()
                // Handle swipe action
//                navController.popBackStack()
//                navController.navigate("current_trip_screen")
            })
        Spacer(modifier = Modifier.height(15.dp))

        //////////////////////////
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //////////////
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(200.dp)
                    .border(0.dp, Color(0xFFB084CC), shape = RoundedCornerShape(5.dp))
                    .padding(start = 12.dp, top = 8.dp, bottom = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.width(65.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .clip(CircleShape)
                                .background(backgroundColorInactiveDOption)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.handshake_chat_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center),
                            )
                        }

                        Text(
                            text = if (homeScreenViewModel.userInstance?.dSettings?.option1 == true) {
                                "On"
                            } else {
                                "Off"
                            },
                            color = if (homeScreenViewModel.userInstance?.dSettings?.option1 == true) {
                                Color(0xFF3CE9BE)
                            } else {
                                Color(0xFFF71735)
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(top = 4.dp)

                        )
                    }


                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.width(65.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .clip(CircleShape)
                                .background(backgroundColorInactiveDOption)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.hryvnia_svg_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center)
                                    .offset(y = (3).dp),
                                tint = Color(0xFF3A3E3F)
                            )

                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.big_cross_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(26.dp)
                                    .align(Alignment.TopStart)
                                    .offset(x = 4.dp, y = (11).dp),
                                tint = Color(0xFFF71735)
                            )
                        }

                        Text(
                            text = if (homeScreenViewModel.userInstance?.dSettings?.option2 == true) {
                                "On"
                            } else {
                                "Off"
                            },
                            color = if (homeScreenViewModel.userInstance?.dSettings?.option2 == true) {
                                Color(0xFF3CE9BE)
                            } else {
                                Color(0xFFF71735)
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(top = 4.dp)

                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
//                    navController.navigate("d_settings_screen")
                    },

                    modifier = Modifier
                        .height(38.dp)
                        .width(170.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = BorderStroke(
                        0.5.dp,
                        Color(0xFF856BFE)
                    )
                )
                {
                    Text("Edit option")
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.Center),
                    colors = IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF8A2BE2),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color(0xFF8A2BE2),
                    ),
                    onClick = {
//                navController.popBackStack()
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.chat_icon),
                        contentDescription = "Close",
                    )
                }
            }
        }
    }
}