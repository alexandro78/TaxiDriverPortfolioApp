package com.taxicardriver.views.screens.registeruserscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cabdriver.R
import com.taxicardriver.viewmodels.UserRegisterViewModel
import com.taxicardriver.viewmodels.userRegisterValidateEmail
import com.taxicardriver.viewmodels.userRegisterValidateName
import com.taxicardriver.viewmodels.userRegisterValidatePassword
import com.taxicardriver.viewmodels.userRegisterValidatePhone
import com.taxicardriver.views.navigation.LocalNavController
import com.taxicardriver.views.scaffoldcontainer.MainScaffoldContainer
import com.taxicardriver.views.screens.authorizationscreen.DashedDivider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterUserScreen() {
    val userRegisterViewModel: UserRegisterViewModel = viewModel()
    val headerText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFBDBDBD), fontSize = 16.sp)) {
            append("Реєстрація нового драйвера")
        }
    }

    MainScaffoldContainer(
        headerText,
        bodyContent = { RegisterUserContentSection(userRegisterViewModel = userRegisterViewModel) },
        bottomBar = {
            RegisterUserConfirmationButton()
        },
        bottomBarcontainerColor = Color(0xFF5F5C68),
        bottomBarHeight = 80
    )
}


@Composable
fun RegisterUserContentSection(userRegisterViewModel: UserRegisterViewModel) {
    val firstName = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFDEDCE6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Ім'я")
        }
    }

    val phone = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFDEDCE6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Телефон")
        }
    }

    val email = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFDEDCE6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Email")
        }
    }

    val password = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFDEDCE6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Пароль")
        }
    }

    val repeatPassword = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFDEDCE6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Повторіть пароль")
        }
    }

    val onFirstNameTextChange = { newText: String ->
        if (userRegisterValidateName(newText)) {
            userRegisterViewModel.firstName = newText
        }
    }
    val onPhoneTextChange = { newText: String ->
        if (userRegisterValidatePhone(newText)) {
            userRegisterViewModel.phone = newText
        }
    }

    val onEmailTextChange = { newText: String ->
        userRegisterViewModel.email = newText
    }

    val onPasswordTextChange = { newText: String ->
        userRegisterViewModel.password = newText
    }

    val onPasswordRepeatTextChange = { newText: String ->
        userRegisterViewModel.repeatPassword = newText
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5F5C68))
            .padding(top = 10.dp)
    ) {
        CustomRegisterInputField(
            label = firstName,
            text = userRegisterViewModel.firstName,
            onTextChange = onFirstNameTextChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (userRegisterViewModel.isInvalidPhoneNumber) {
            Text(
                "Некоректний номер телефону",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFEF5350)
            )
        } else {
            Text(
                "Телефон в форматі 380 без знаку +",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFDEDCE6)
            )
        }

        CustomRegisterInputField(
            label = phone,
            text = userRegisterViewModel.phone,
            onTextChange = onPhoneTextChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (userRegisterViewModel.isEmailInvalid) {
            Text(
                "Некоректний Email",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFEF5350)
            )
        }

        if (userRegisterViewModel.emailInUseMessage != "") {
            Text(
                userRegisterViewModel.emailInUseMessage,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFEF5350)
            )
        }

        CustomRegisterInputField(
            label = email,
            text = userRegisterViewModel.email,
            onTextChange = onEmailTextChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Пароль складається з латинських символів та чисел, довжиною від 8 до 16 символів",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 12.dp),
            color = Color(0xFFDEDCE6)
        )

        if (userRegisterViewModel.passwordStatus != "") {
            Text(
                userRegisterViewModel.passwordStatus,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFEF5350)
            )
        }

        CustomRegisterPasswordInputField(
            label = password,
            text = userRegisterViewModel.password,
            onTextChange = onPasswordTextChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!userRegisterViewModel.passwordMatch) {
            Text(
                "Паролі не співпадають",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp),
                color = Color(0xFFEF5350)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))
        CustomRegisterPasswordInputField(
            label = repeatPassword,
            text = userRegisterViewModel.repeatPassword,
            onTextChange = onPasswordRepeatTextChange
        )

        Spacer(modifier = Modifier.height(70.dp))
        UserLoginReference()
    }
}


@Composable
fun CustomRegisterInputField(label: AnnotatedString, text: String, onTextChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFFF7F3F3),
                unfocusedTextColor = Color(0xFFE2D8D8),
                cursorColor = Color(0xFFF7F3F3),
                unfocusedBorderColor = Color(0xFF49D7E9),
                focusedBorderColor = Color(0xFFFFEE58),
                focusedLabelColor = Color(0xFF7CEB53),
                unfocusedLabelColor = Color(0xFFC8EEBA),
            ),
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            label = { Text(label) }
        )
    }
}


@Composable
fun CustomRegisterPasswordInputField(
    label: AnnotatedString,
    text: String,
    onTextChange: (String) -> Unit
) {
    var passwordIsVisible by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFFF7F3F3),
                unfocusedTextColor = Color(0xFFE2D8D8),
                cursorColor = Color(0xFFF7F3F3),
                unfocusedBorderColor = Color(0xFF49D7E9),
                focusedBorderColor = Color(0xFFFFEE58),
                focusedLabelColor = Color(0xFF7CEB53),
                unfocusedLabelColor = Color(0xFFC8EEBA),
            ),
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            label = { Text(label) },
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
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterUserConfirmationButton() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userRegisterViewModel: UserRegisterViewModel = viewModel()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    modifier = Modifier,
                    snackbarData = it,
                    containerColor = Color(0xFF26A69A),
                    contentColor = Color(0xFFF8F8F2),
                    actionColor = Color(0xFFE2F14E),
                    actionContentColor = DefaultShadowColor,
                    shape = RoundedCornerShape(10.dp),
                )
            }
        },
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF5F5C68))
                .offset(y = (-2).dp),
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
                    userRegisterViewModel.clickedStatus = !userRegisterViewModel.clickedStatus
                    userRegisterViewModel.isInvalidPhoneNumber =
                        userRegisterViewModel.phone.length in 11 downTo 0
                    userRegisterViewModel.isEmailInvalid =
                        !userRegisterValidateEmail(userRegisterViewModel.email)
                    userRegisterViewModel.passwordStatus =
                        userRegisterValidatePassword(userRegisterViewModel.password)
                    userRegisterViewModel.passwordMatch =
                        userRegisterViewModel.password == userRegisterViewModel.repeatPassword
//                    if (userRegisterViewModel.successMessage) {
//                        navController.navigate("start_page") {
//                            popUpTo(route = "start_page") { inclusive = true }
//                        }
//                    }
                }) {
                Text(
                    "Ok",
                    fontSize = 20.sp
                )
            }


            LaunchedEffect(userRegisterViewModel.clickedStatus) {
                if (userRegisterViewModel.firstName != ""
                    && userRegisterViewModel.phone != ""
                    && userRegisterViewModel.email != ""
                    && userRegisterViewModel.passwordStatus == ""
                    && userRegisterViewModel.password != ""
                    && userRegisterViewModel.passwordMatch
                    && !userRegisterViewModel.isInvalidPhoneNumber
                    && !userRegisterViewModel.isEmailInvalid
                ) {

                    userRegisterViewModel.registerUser(
                        email = userRegisterViewModel.email,
                        password = userRegisterViewModel.password,
                        firstName = userRegisterViewModel.firstName,
                        phone = userRegisterViewModel.phone,
                    )

                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = "Користувач зареєстрований",
                                actionLabel = "Продовжити",
                                duration = SnackbarDuration.Short
                            )

                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                /* Handle snackbar action performed */
                            }

                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserLoginReference(){
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    var registrationRouteRefColor by remember { mutableLongStateOf(0xFF00F0B5) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .layoutId("RowConstraintR"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClickableText(
            modifier = Modifier,
            text = AnnotatedString("Вже маєте акаунт, увійти"),
            onClick = {
                registrationRouteRefColor = 0xFFB084CC
                scope.launch {
                    delay(100) // задержка в 10 миллисекунд
                    registrationRouteRefColor = 0xFF00F0B5
                }
                navController.navigate("auth_user_screen")
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
                .width(188.dp)
                .padding(top = 2.dp)
        )
    }
}
