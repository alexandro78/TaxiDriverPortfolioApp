package com.taxicardriver.views.servicecomponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.cabdriver.R
import com.taxicardriver.viewmodels.HomeScreenViewModel

@Composable
fun ServiceRadiusButton(homeScreenViewModel: HomeScreenViewModel) {
    var carArrivalRadius: Float? by remember { mutableStateOf(null) }

    LaunchedEffect(homeScreenViewModel.userInstance?.driver?.carArrivalRadius) {
        carArrivalRadius = homeScreenViewModel.userInstance?.driver?.carArrivalRadius
    }

    Log.d("FF", "FF5 " + homeScreenViewModel.setCarArrivalRadius.toString())
    LaunchedEffect(homeScreenViewModel.setCarArrivalRadius) {
        carArrivalRadius = homeScreenViewModel.setCarArrivalRadius
        Log.d("FF", "FF " + homeScreenViewModel.setCarArrivalRadius.toString())
    }



    ConstraintLayout(funConstrainSetServiceRadiusButton(), modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("serviceRadiusButton"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center){
                FloatingActionButton(modifier = Modifier
                    .offset(x = 20.dp)
                    .size(120.dp, 38.dp),
                    shape = RoundedCornerShape(18.dp),
                    containerColor = Color(0xFFC4D7F2),
                    onClick = { }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                carArrivalRadius = carArrivalRadius?.let {
                                    if (it > 0.6f) {
                                        String.format("%.1f", it - 0.1f).toFloat()
                                    } else {
                                        it
                                    }
                                }
                                carArrivalRadius?.let {
                                    homeScreenViewModel.updateCarArrivalRadius(
                                        it
                                    )
                                }
                            },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.remove_icon),
                                contentDescription = "Sub",
                            )
                        }

                        Text(
                            text = "${carArrivalRadius ?: "0"} км",
                            fontSize = 16.sp,
                            color = Color(0xFF247BA0)
                        )

                        IconButton(
                            onClick = {
//                                carArrivalRadius = carArrivalRadius?.plus(0.1f)
                                carArrivalRadius = carArrivalRadius?.plus(0.1f)?.let {
                                    String.format("%.1f", it).toFloat()
                                }
                                carArrivalRadius?.let {
                                    homeScreenViewModel.updateCarArrivalRadius(
                                        it
                                    )
                                }
                            },
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                            )
                        }
                    }
                }
            }
        }
    }
}


private fun funConstrainSetServiceRadiusButton(): ConstraintSet {
    return ConstraintSet {
        val filterFloatButton = createRefFor("serviceRadiusButton")
        val horizontalGuideline = createGuidelineFromTop(0.95f)

        constrain(filterFloatButton) {
            top.linkTo(horizontalGuideline)
        }
    }
}