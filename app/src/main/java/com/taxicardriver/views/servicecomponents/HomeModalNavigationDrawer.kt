package com.taxicardriver.views.servicecomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cabdriver.R
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.viewmodels.HomeScreenViewModel
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer
import com.taxicardriver.views.screens.HomeScreenBottomBarSection
import com.taxicardriver.views.screens.HomeScreenContentSection
import com.taxicardriver.views.screens.HomeScreenHeadIconButton
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeModalNavigationDrawer(
    headerText: AnnotatedString,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    val navController = LocalNavController.current
    var selectedItemStatus by remember { mutableStateOf(false) }
    val mainViewModel: MainViewModel = viewModel()
    val homeScreenViewModel: HomeScreenViewModel = viewModel()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)

            ) {
                ModalDrawerSheet(
                    drawerContentColor = Color(0xFFECECEE),
                    drawerContainerColor = Color(0xFF222429)
                ) {

                    Box {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Box(modifier = Modifier.padding(1.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.handshake_chat_icon),
                                    contentDescription = "Image Description",
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(100.dp)
                                        .clip(CircleShape)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                            ) {
                                Column {
                                    Text(
                                        "Username",
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .offset(y = 6.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row() {
                                        Box(
                                            modifier = Modifier
                                                .offset(x = 10.dp, y = 4.dp)
                                                .border(
                                                    0.3.dp,
                                                    Color(0xFFA135D0),
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(4.dp)

                                        ) {
                                            Row(
                                                modifier = Modifier,
                                                verticalAlignment = Alignment.Bottom
                                            ) {
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(id = R.drawable.grade_star_icon),
                                                    "contentDescription",
                                                    modifier = Modifier
                                                        .size(28.dp),
                                                    tint = Color(0xFFFFA726),
                                                )
                                                Text(
                                                    homeScreenViewModel.userInstance?.rating.toString(),
                                                    fontSize = 18.sp,
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 4.dp,
                                                            end = 2.dp,
                                                            top = 2.dp,
                                                            bottom = 2.dp
                                                        )
                                                )
                                            }
                                        }
                                        Box(
                                            modifier = Modifier
                                                .offset(x = 55.dp, y = 2.dp)
                                                .border(
                                                    0.2.dp,
                                                    Color(0xFF79767A),
                                                    shape = RoundedCornerShape(18.dp)
                                                )
                                                .clickable {
//                                                    navController.navigate("personal_data_screen")
                                                }
                                        ) {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(id = R.drawable.edit_icon),
                                                "contentDescription",
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .size(18.dp),
                                                tint = Color(0xFF88878D),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    HorizontalDivider()
                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 14.dp, end = 26.dp)
                            .height(44.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFF222429),
                            selectedContainerColor = Color(0xFFFFA726),
                            selectedTextColor = Color(0xFFECECEE),
                            unselectedTextColor = Color(0xFFECECEE),
                        ),

                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.sportcar_svg_icon),
                                "contentDescription",
                                modifier = Modifier
                                    .size(40.dp)
                                    .offset(x = (-3).dp, y = 8.dp),
                                tint = Color(0xFF9E9DA3),
                            )
                        },
                        label = {
                                Text(
                                    text = "Авто",
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .offset(y = 5.dp)
                                )
                        },
                        selected = selectedItemStatus,
                        onClick = {
                            selectedItemStatus = !selectedItemStatus
                            navController.navigate("vehicle_screen")
                            selectedItemStatus = !selectedItemStatus
                        }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(top = 14.dp, start = 14.dp, end = 26.dp)
                            .height(40.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFF222429),
                            selectedContainerColor = Color(0xFFFF7043),
                            selectedTextColor = Color(0xFFECECEE),
                            unselectedTextColor = Color(0xFFECECEE),
                        ),
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.history_icon),
                                "contentDescription",
                                modifier = Modifier
                                    .size(26.dp),
                                tint = Color(0xFF9E9DA3),
                            )
                        },
                        label = {
                            Text(
                                text = "Поїздки",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                            )
                        },
                        selected = selectedItemStatus,
                        onClick = {
                            selectedItemStatus = !selectedItemStatus
//                            navController.navigate("ride_history_screen")
                            selectedItemStatus = !selectedItemStatus
                        }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 14.dp, end = 26.dp)
                            .height(40.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFF222429),
                            selectedContainerColor = Color(0xFFFF7043),
                            selectedTextColor = Color(0xFFECECEE),
                            unselectedTextColor = Color(0xFFECECEE),
                        ),
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.credit_card_icon),
                                "contentDescription",
                                modifier = Modifier
                                    .size(26.dp),
                                tint = Color(0xFF9E9DA3),
                            )
                        },
                        label = {
                            Text(
                                text = "Платежі",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                            )
                        },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 14.dp, end = 26.dp)
                            .height(40.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFF222429),
                            selectedContainerColor = Color(0xFFFF7043),
                            selectedTextColor = Color(0xFFECECEE),
                            unselectedTextColor = Color(0xFFECECEE),
                        ),
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.settings_icon),
                                "contentDescription",
                                modifier = Modifier
                                    .size(26.dp),
                                tint = Color(0xFF9E9DA3),
                            )
                        },
                        label = {
                            Text(
                                text = "Налаштування",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                            )
                        },
                        selected = false,
                        onClick = { /*navController.navigate("profile_setting_screen")*/ }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 14.dp, end = 26.dp)
                            .height(40.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFF222429),
                            selectedContainerColor = Color(0xFFFF7043),
                            selectedTextColor = Color(0xFFECECEE),
                            unselectedTextColor = Color(0xFFECECEE),
                        ),
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.notifications_icon),
                                "contentDescription",
                                modifier = Modifier
                                    .size(26.dp),
                                tint = Color(0xFFFFA726),
                            )
                        },
                        label = {
                            Text(
                                text = "Новини",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                            )
                        },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                    // ...other drawer items
                }
            }
        }
    ) {
        MainScaffoldContainer(
            headerText,
            navIcon = {
                HomeScreenHeadIconButton(
                    drawerState = drawerState,
                    scope = scope
                )
            },
            bodyContent = { HomeScreenContentSection() },
            filterFloatButton = { FilterFloatButton() },
            serviceRadiusButton = { ServiceRadiusButton(homeScreenViewModel = homeScreenViewModel) },
            bottomBar = { HomeScreenBottomBarSection() },
            bottomBarcontainerColor = Color(0xFF000000),
            bottomBarHeight = if (!mainViewModel.isOnlineModActive) {
                240
            } else {
                80
            }
        )
    }
}