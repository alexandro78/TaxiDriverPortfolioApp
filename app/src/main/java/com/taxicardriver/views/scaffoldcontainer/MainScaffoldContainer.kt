package com.taxicardriver.views.scaffoldcontainer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taxicardriver.viewmodels.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffoldContainer(
    headerText: AnnotatedString,
    navIcon: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
    emptyAction: Boolean = false,
    bodyContent: @Composable () -> Unit,
    filterFloatButton: @Composable (() -> Unit)? = null,
    serviceRadiusButton: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    bottomBarcontainerColor: Color = Color.Transparent,
    bottomBarHeight: Int = 240

) {
    val mainViewModel: MainViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF111113),
                    titleContentColor = Color(0xFFEF5350),
                ),
                navigationIcon = {
                    navIcon?.let { it() }
                },
                actions = {
                    action?.let { it() }
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(x = if (emptyAction) {
                                0.dp
                                } else {
                                (-24).dp
                              }),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = headerText,
                            color = Color(0xFFBDBDBD),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier,
                        )
                    }
                }
            )
        },
        bottomBar = {

            if (bottomBar != null) {
                BottomAppBar(
                    modifier = Modifier
                        .height(bottomBarHeight.dp)
                        .fillMaxWidth(),
                    containerColor = bottomBarcontainerColor,
                ) {
                    bottomBar()
                }
            }
        },
        floatingActionButton = {
            if (filterFloatButton != null || serviceRadiusButton != null) {
                if (filterFloatButton != null) {
                    filterFloatButton()
                }

                if (serviceRadiusButton != null) {
                    serviceRadiusButton()
                }
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
            ) {
                bodyContent()
            }
        },
    )
}



