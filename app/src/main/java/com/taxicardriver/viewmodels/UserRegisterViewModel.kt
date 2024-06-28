package com.taxicardriver.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taxicardriver.models.dtos.UserRegisterData
import com.taxicardriver.network.connectbuilders.httpbuilders.RetrofitBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.regex.Pattern

class UserRegisterViewModel : ViewModel(){
    var clickedStatus by mutableStateOf(false)
    var isInvalidPhoneNumber by mutableStateOf(false)
    var isEmailInvalid by mutableStateOf(false)
    var passwordStatus by mutableStateOf("")
    var passwordMatch by mutableStateOf(true)
    var successMessage by mutableStateOf(false)
    var emailInUseMessage by mutableStateOf("")


    var firstName by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var repeatPassword by mutableStateOf("")


    private val apiService = RetrofitBuilder.userRegisterApiService

    fun registerUser(email: String, password: String, firstName: String, phone: String) {
        val userRegisterData = UserRegisterData(email, password, firstName, phone)
        apiService.registerUser(userRegisterData).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Operation successfully completed
                    successMessage = response.isSuccessful
                    val responseString = response.body()?.string() ?: "No response body"
                    Log.d("test7", "response= $responseString")
                } else {
                    // Error handling if the response status is not "successful"
                    emailInUseMessage = response.errorBody()?.string() ?: "Unknown error"
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("test7", "Error: $errorMessage")
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handling a request failure, such as network problems
            }
        })
    }
}


//////Password validator
internal fun userRegisterValidatePassword(passwordPolicyValidation: String): String {
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


//////Email validator
fun userRegisterValidateEmail(email: String): Boolean {
    val emailRegex = "^[\\w.]+@[\\w.]*[a-zA-Z]*$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(email)

    return matcher.matches()
}


//////Name validator
fun userRegisterValidateName(name: String): Boolean {
    val nameRegex = "^[а-яА-ЯёЁ-]+$"
    val pattern = Pattern.compile(nameRegex)
    val matcher = pattern.matcher(name)

    return matcher.matches() && name.length <= 100
}


//////Date validator
fun userRegisterValidateDateInput(date: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return try {
        LocalDate.parse(date, formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}


///phone validator
fun userRegisterValidatePhone(phone: String): Boolean {
    val phoneRegex = "^\\d*$"
    val pattern = Pattern.compile(phoneRegex)
    val matcher = pattern.matcher(phone)

    return matcher.matches()
}






