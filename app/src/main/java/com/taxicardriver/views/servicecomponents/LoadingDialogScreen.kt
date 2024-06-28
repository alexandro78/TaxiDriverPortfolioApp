package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taxicardriver.views.navigation.LocalNavController

@Composable
fun LoadingDialogScreen(serverConnectProblem: Boolean, serverConnectProblemType: String) {
    val navController = LocalNavController.current
    Dialog(
        onDismissRequest = { /*onDismissRequest()*/ },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000)),
            Alignment.Center
        ) {
            if (!serverConnectProblem) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    strokeCap = StrokeCap.Round,
                    color = Color(0xFFB084CC),
                    trackColor = Color(0xFF00F0B5)
                )
            } else {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ClickableText(
                        text = AnnotatedString("$serverConnectProblemType\n Сервер не відповідає, можливо проблема з інтернетом"),
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xFFFF7043),
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(50.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate("start_page")
                        },

                        modifier = Modifier
                            .height(47.dp)
                            .width(140.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF0F6F65)
                        ),
                        border = BorderStroke(
                            0.5.dp,
                            Color(0xFF00C2E0)
                        )
                    ) {
                        Text(
                            text = "Повторити",
                            color = Color(0xFFECECEE),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}