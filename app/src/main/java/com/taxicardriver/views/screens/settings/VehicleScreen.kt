package com.taxicardriver.views.screens.settings


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cabdriver.R
import com.taxicardriver.viewmodels.MainViewModel
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer


@Composable
fun VehicleScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("Авто")
        }
    }

    MainScaffoldContainer(
        headerText,
        navIcon = { VehicleNavIconHeaderSection() },
        action = {if(!mainViewModel.activeVehicleTab){ActionHeaderIcon()}},
        emptyAction = !mainViewModel.activeVehicleTab,
        bodyContent = { MainVehicleContentSection() },
        bottomBar = { BottomBarVehicleListSection() },
        bottomBarcontainerColor = Color(0xFF000000),
        bottomBarHeight = if (!mainViewModel.activeVehicleTab) {
            100
        } else {
            0
        }
    )
}


@Composable
fun VehicleNavIconHeaderSection() {
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
fun ActionHeaderIcon() {
    IconButton(
        modifier = Modifier,
        colors = IconButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF8A2BE2),
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color(0xFF8A2BE2),
        ),
        onClick = {
        },
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Close",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(30.dp),
            tint = Color(0xFF17C3B2)
        )
    }
}


@Composable
fun MainVehicleContentSection() {
    val mainViewModel: MainViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(start = 2.dp, end = 2.dp)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1D1D1C))
            .drawWithContent {
                if (mainViewModel.activeVehicleTab) {
                    drawLine(
                        color = Color(0xFF17C3B2),
                        start = Offset(10f, size.height),
                        end = Offset(size.width / 2 - 10, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                } else {
                    drawLine(
                        color = Color(0xFF17C3B2),
                        start = Offset(size.width / 2 + 10, size.height),
                        end = Offset(size.width - 10, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                drawContent()
            }) {
            TextButton(modifier = Modifier
                .height(60.dp)
                .weight(1f)
                .padding(5.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFBCED09),
                    containerColor = Color(0xFF2D2F2E)
                ),
                onClick = { mainViewModel.activeVehicleTab = true }) {
                Text(
                    "Активне",
                    color = Color(0xFFBCED09),
                    fontSize = 18.sp,
                    modifier = Modifier,
                )
            }

            TextButton(modifier = Modifier
                .height(60.dp)
                .weight(1f)
                .padding(5.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFBCED09),
                    containerColor = Color(0xFF2D2F2E)
                ),
                onClick = { mainViewModel.activeVehicleTab = false }) {
                Text(
                    "Список",
                    color = Color(0xFFBCED09),
                    fontSize = 18.sp,
                    modifier = Modifier,
                )
            }
        }

        if (mainViewModel.activeVehicleTab) {
            ActiveVehicleContentSection()
        } else {
            VehicleListContentSection()
        }
    }
}


@Composable
fun ActiveVehicleContentSection() {
    var luxClassChecked by remember { mutableStateOf(false) }
    var comfortClassChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 4.dp, start = 1.dp, end = 1.dp, bottom = 3.dp)
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFF636460))
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_image_png),
            contentDescription = "",
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 3.dp, start = 1.dp, end = 1.dp, bottom = 3.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Car Model Name",
            color = Color(0xFFE9EBE6),
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .border(
                        0.6.dp,
                        Color(0xFFE1C5EB),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "ОІ6490АІ",
                    color = Color(0xFFE9EBE6),
                    fontSize = 20.sp
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp, bottom = 3.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Колір: чорний",
            color = Color(0xFFE9EBE6),
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar_icon_blue_png),
                contentDescription = "",
                modifier = Modifier
                    .size(38.dp),
            )

            Text(
                text = "2020",
                color = Color(0xFFE9EBE6),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp, end = 8.dp)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Класи авто",
            color = Color(0xFFE9EBE6),
            fontSize = 20.sp,
            modifier = Modifier
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 7.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = comfortClassChecked,
            colors = SwitchDefaults.colors(
                checkedBorderColor = Color(0xFF3CE9BE),
                checkedIconColor = Color(0xFF3CE9BE),
                checkedThumbColor = Color(0xFF3CE9BE),
                checkedTrackColor = Color(0xFF2B2C2E),
                uncheckedBorderColor = Color(0xFF8B8486),
                uncheckedThumbColor = Color(0xFF8B8486),
                uncheckedTrackColor = Color(0xFF2B2C2E),
            ),
            onCheckedChange = {
                comfortClassChecked = it
            },
            thumbContent = if (comfortClassChecked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )

        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Комфорт",
            modifier = Modifier,
            style = TextStyle(
                fontSize = 20.sp,
                color = if (comfortClassChecked) {
                    Color(0xFF3CE9BE)
                } else {
                    Color(0xFFE9EBE6)
                },
            )
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 2.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = luxClassChecked,
            colors = SwitchDefaults.colors(
                checkedBorderColor = Color(0xFF3CE9BE),
                checkedIconColor = Color(0xFF3CE9BE),
                checkedThumbColor = Color(0xFF3CE9BE),
                checkedTrackColor = Color(0xFF2B2C2E),
                uncheckedBorderColor = Color(0xFF8B8486),
                uncheckedThumbColor = Color(0xFF8B8486),
                uncheckedTrackColor = Color(0xFF2B2C2E),
            ),
            onCheckedChange = {
                luxClassChecked = it
            },
            thumbContent = if (luxClassChecked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )

        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Люкс",
            modifier = Modifier,
            style = TextStyle(
                fontSize = 20.sp,
                color = if (luxClassChecked) {
                    Color(0xFF3CE9BE)
                } else {
                    Color(0xFFE9EBE6)
                },
            )
        )
    }

    HorizontalDivider(
        modifier = Modifier.padding(
            top = 14.dp,
            start = 3.dp,
            end = 3.dp,
            bottom = 14.dp
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableText(
            text = AnnotatedString("Документи на авто"),
            onClick = { },
            modifier = Modifier
                .padding(start = 8.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFFE9EBE6),
            )
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 2.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2F2E))
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableText(
            text = AnnotatedString("Фото авто"),
            onClick = { },
            modifier = Modifier
                .padding(start = 8.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFFE9EBE6),
            )
        )
    }
}


@Composable
fun VehicleListContentSection() {
    val mainViewModel: MainViewModel = viewModel()
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(2.dp)
    ) {
        // Add 5 items
        items(12) { index ->
            if (mainViewModel.activeVehicleItemId == index) {
                CheckedVehicleItem(index = index)
            } else {
                VehicleItem(index = index)
            }
        }
    }
}


@Composable
fun CheckedVehicleItem(index: Int) {
    val mainViewModel: MainViewModel = viewModel()
    Card(
        modifier = Modifier.padding(top = 4.dp, start = 2.dp, end = 2.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 5.dp
        ),
        onClick = {
            mainViewModel.activeVehicleItemId = if (mainViewModel.activeVehicleItemId != index) {
                index
            } else {
                -1
            }
        },
        border = BorderStroke(1.dp, color = Color(0xFF26A69A)),
        colors = CardColors(
            containerColor = Color(0xFF2E2E31),
            contentColor = Color(0xFF2E2E31),
            disabledContainerColor = Color(0xFF2E2E31),
            disabledContentColor = Color(0xFF2E2E31),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Car Model Name",
                    fontSize = 20.sp,
                    color = Color(0xFFD0D0DA),
                    modifier = Modifier
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "ОІ4638АІ",
                        fontSize = 20.sp,
                        color = Color(0xFFD0D0DA),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (mainViewModel.activeVehicleItemId == index) {
                    Text(
                        text = "Обрано",
                        fontSize = 20.sp,
                        color = Color(0xFF26A69A),
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Дніпро",
                        fontSize = 20.sp,
                        color = if (mainViewModel.activeVehicleItemId == index) {
                            Color(0xFF26A69A)
                        } else {
                            Color(0xFFBECECC)
                        },
                        modifier = Modifier.padding(end = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun VehicleItem(index: Int) {
    val mainViewModel: MainViewModel = viewModel()
    Card(
        modifier = Modifier.padding(top = 4.dp, start = 2.dp, end = 2.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 5.dp
        ),
        onClick = {
            mainViewModel.activeVehicleItemId = if (mainViewModel.activeVehicleItemId != index) {
                index
            } else {
                -1
            }
        },
        border = BorderStroke(1.dp, color = Color(0xFFAEC2C0)),
        colors = CardColors(
            containerColor = Color(0xFF2E2E31),
            contentColor = Color(0xFF2E2E31),
            disabledContainerColor = Color(0xFF2E2E31),
            disabledContentColor = Color(0xFF2E2E31),
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Car Model Name",
                    fontSize = 20.sp,
                    color = Color(0xFFD0D0DA),
                    modifier = Modifier
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "ОІ4638АІ",
                        fontSize = 20.sp,
                        color = Color(0xFFD0D0DA),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (mainViewModel.activeVehicleItemId == index) {
                    Text(
                        text = "Обрано",
                        fontSize = 20.sp,
                        color = Color(0xFF26A69A),
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Дніпро",
                        fontSize = 20.sp,
                        color = if (mainViewModel.activeVehicleItemId == index) {
                            Color(0xFF26A69A)
                        } else {
                            Color(0xFFBECECC)
                        },
                        modifier = Modifier.padding(end = 4.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun BottomBarVehicleListSection() {
    ChoseActiveVehicleConfirmationButton()
}


@Composable
fun ChoseActiveVehicleConfirmationButton() {
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
                containerColor = Color(0xFF17C3B2)
            ),
            onClick = {
                navController.navigate("start_page")
            }) {
            Text(
                "Ok",
                fontSize = 20.sp
            )
        }
    }
}