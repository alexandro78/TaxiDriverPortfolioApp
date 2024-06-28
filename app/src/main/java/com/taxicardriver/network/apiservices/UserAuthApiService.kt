package com.taxicardriver.network.apiservices

import com.taxicardriver.models.dtos.UserLoginData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthApiService {
    @POST("auth/login")
    fun loginUser(@Body userLoginData: UserLoginData): Call<ResponseBody>
}
