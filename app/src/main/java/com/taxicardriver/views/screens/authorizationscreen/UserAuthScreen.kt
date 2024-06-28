package com.taxicardriver.views.screens.authorizationscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.cabdriver.R
import com.taxicardriver.viewmodels.UserAuthViewModel
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun UserAuthScreen() {
    val userAuthViewModel: UserAuthViewModel = viewModel()
    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("CARDRIVER")
        }
    }

    MainScaffoldContainer(
        headerText,
        bodyContent = { UserAuthContentSection(userAuthViewModel = userAuthViewModel) },
        bottomBarHeight = 0
    )
}

@Composable
fun UserAuthContentSection(userAuthViewModel: UserAuthViewModel) {
    Column(
        modifier = Modifier
            .background(Color(0xFF685369))
    ) {
        ConstraintContainer(userAuthViewModel = userAuthViewModel)
    }
}


private fun funConstrainSet(): ConstraintSet {
    return ConstraintSet {
        val columnRef = createRefFor("columnConstraintR")
        val columnRegisterRef = createRefFor("RowConstraintR")
        val centerHorizontalguideline = createGuidelineFromTop(0.3f)
        val bottomHorizontalguideline = createGuidelineFromTop(0.92f)

        constrain(columnRef) {
            top.linkTo(centerHorizontalguideline)
        }

        constrain(columnRegisterRef) {
            top.linkTo(bottomHorizontalguideline)
        }
    }
}

@Composable
fun ConstraintContainer(userAuthViewModel: UserAuthViewModel) {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    var registrationRouteRefColor by remember { mutableLongStateOf(0xFF00F0B5) }
    ConstraintLayout(funConstrainSet(), modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .layoutId("columnConstraintR"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                if (userAuthViewModel.badCredentialError) {
                    Text(
                        "Неправильний email або пароль",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 6.dp),
                        color = Color(0xFFEF5350)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                if (!userAuthViewModel.userAuthEmailIsValid) {
                    Text(
                        "Некоректний email",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 6.dp),
                        color = Color(0xFFEF5350)
                    )
                }
            }
            if (userAuthViewModel.userAuthEmailIsValid && !userAuthViewModel.badCredentialError) {
                Spacer(modifier = Modifier.height(20.dp))
            }
            LoginInputField(userAuthViewModel = userAuthViewModel)

            if (userAuthViewModel.userAuthPasswordError == "") {
                Spacer(modifier = Modifier.height(50.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                if (userAuthViewModel.userAuthPasswordError != "") {
                    Text(
                        userAuthViewModel.userAuthPasswordError,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 5.dp, top = 20.dp),
                        color = Color(0xFFEF5350)
                    )
                }
            }
            PasswordInputField(userAuthViewModel = userAuthViewModel)
            Spacer(modifier = Modifier.height(40.dp))
            LoginButton(userAuthViewModel = userAuthViewModel)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("RowConstraintR"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClickableText(
                modifier = Modifier,
                text = AnnotatedString("Зареєструватися"),
                onClick = {
                    registrationRouteRefColor = 0xFFB084CC
                    scope.launch {
                        delay(100) // задержка в 10 миллисекунд
                        registrationRouteRefColor = 0xFF00F0B5
                    }
                    navController.navigate("register_user_screen")
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(registrationRouteRefColor),
                )
            )
            DashedDivider(
                color = Color(0xFF00F0B5),
                thickness = 1.dp,
                intervals = floatArrayOf(4f, 4f),
                modifier = Modifier
                    .width(132.dp)
                    .padding(top = 2.dp)
            )
        }
    }
}


@Composable
fun LoginInputField(userAuthViewModel: UserAuthViewModel) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFFF7F3F3),
            unfocusedTextColor = Color(0xFFE2D8D8),
            cursorColor = Color(0xFFF7F3F3),
            unfocusedBorderColor = Color(0xFF49D7E9),
            focusedBorderColor = Color(0xFFFFEE58),
            focusedLabelColor = Color(0xFF7CEB53),
            unfocusedLabelColor = Color(0xFFC8EEBA),
        ),
        value = userAuthViewModel.email,
        onValueChange = { newEmail ->
            userAuthViewModel.onEmailChange(newEmail)
        },
        label = {
            Text(
                text = "Email",
                style = TextStyle(fontSize = 16.sp)
            )
        }
    )
}


@Composable
fun PasswordInputField(userAuthViewModel: UserAuthViewModel) {
    var passwordIsVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFFF7F3F3),
            unfocusedTextColor = Color(0xFFE2D8D8),
            cursorColor = Color(0xFFF7F3F3),
            unfocusedBorderColor = Color(0xFF49D7E9),
            focusedBorderColor = Color(0xFFFFEE58),
            focusedLabelColor = Color(0xFF7CEB53),
            unfocusedLabelColor = Color(0xFFC8EEBA),
        ),
        value = userAuthViewModel.password,
        onValueChange = { newPassword ->
            userAuthViewModel.onPasswordChange(newPassword)
        },
        label = {
            Text(
                text = "Пароль",
                style = TextStyle(fontSize = 16.sp)
            )
        },
        textStyle = TextStyle(fontSize = 18.sp),
        trailingIcon = {
            val visibilityIcon = if (passwordIsVisible) {
                ImageVector.vectorResource(id = R.drawable.visibility_on_icon)
            } else {
                ImageVector.vectorResource(id = R.drawable.visibility_off_icon)
            }

            Icon(
                imageVector = visibilityIcon,
                "contentDescription",
                modifier = Modifier
                    .clickable {
                        passwordIsVisible = !passwordIsVisible
                    }
                    .size(26.dp),
                tint = Color(0xFFB084CC),
            )
        },
        visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(
            '●'
        ),
        keyboardOptions = KeyboardOptions(keyboardType = if (passwordIsVisible) KeyboardType.Text else KeyboardType.Password),
    )
}


@Composable
fun LoginButton(userAuthViewModel: UserAuthViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            modifier = Modifier.size(200.dp, 55.dp),
            onClick = {
                userAuthViewModel.badCredentialError = false

                userAuthViewModel.userAuthEmailIsValid =
                    userAuthViewModel.userAuthValidateEmail(userAuthViewModel.email)

                userAuthViewModel.userAuthPasswordError =
                    userAuthViewModel.userAuthValidatePassword(userAuthViewModel.password)

                if (userAuthViewModel.userAuthValidateEmail(userAuthViewModel.email) &&
                    userAuthViewModel.userAuthValidatePassword(
                        userAuthViewModel.password
                    ) == ""
                ) {
                    userAuthViewModel.loginUser(
                        email = userAuthViewModel.email,
                        password = userAuthViewModel.password
                    )
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFFBCED09),
            ),
        ) {
            Text(
                "Увійти",
                fontSize = 18.sp,
                modifier = Modifier.shadow(30.dp),
                color = Color(0xFF080908)
            )
        }
    }
}


@Composable
fun DashedDivider(
    thickness: Dp,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    modifier: Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color,
            style = Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = intervals,
                    phase = phase
                )
            )
        )
    }
}

