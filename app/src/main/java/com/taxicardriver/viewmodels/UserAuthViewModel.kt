package com.taxicardriver.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taxicardriver.models.dtos.UserLoginData
import com.taxicardriver.network.connectbuilders.httpbuilders.RetrofitBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class UserAuthViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var userAuthEmailIsValid by mutableStateOf(true)
    var userAuthPasswordError by mutableStateOf("")
    var badCredentialError by mutableStateOf(false)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    private val authService = RetrofitBuilder.userAuthApiService

    fun loginUser(email: String, password: String) {
        val userLoginData = UserLoginData(email, password)
        authService.loginUser(userLoginData).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Successful authentication, process server response
                    badCredentialError = false
                    val responseString = response.body()?.string() ?: "No response body"
                    Log.d("Login", "Login successful: $responseString")
                } else {
                    // Error handling
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("Login", "Login failed: $errorMessage")
                    if (errorCode == 400) {
                        badCredentialError = true
                        Log.e("Login", "Error code 400 true: $errorCode")
                    } else {
                        Log.e("Login", "Another Error code: $errorCode")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handling network errors and other exceptions
                Log.e("Login", "Login error: ${t.message}")
            }
        })
    }


    //////Email validator
    fun userAuthValidateEmail(email: String): Boolean {
        val emailRegex = "^[\\w.]+@[\\w.]*[a-zA-Z]*$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }


    //////Password validator
    internal fun userAuthValidatePassword(passwordPolicyValidation: String): String {
        val maxPasswordLength = 16
        val minPasswordLength = 8
        val specialCharactersRegex = "[!@#\$%^&*(),.?\":{}|<>]".toRegex()
        val latinAlphabetRegex = "[a-zA-Z0-9]+".toRegex()
        var checkedPassword = ""

        if (passwordPolicyValidation.length > maxPasswordLength) {
            checkedPassword = "Пароль повинен містити максимум 16 символів!"
        } else if (passwordPolicyValidation.length < minPasswordLength) {
            checkedPassword = "Пароль повинен містити мінімум 8 символів!"
        } else if (specialCharactersRegex.containsMatchIn(passwordPolicyValidation)) {
            checkedPassword = "Пароль не повинен містити спеціальні символи!"
        } else if (!latinAlphabetRegex.matches(passwordPolicyValidation)) {
            checkedPassword = "Пароль повинен складатись тільки з символів латинського алфавіту!"
        }

        return checkedPassword
    }
}
