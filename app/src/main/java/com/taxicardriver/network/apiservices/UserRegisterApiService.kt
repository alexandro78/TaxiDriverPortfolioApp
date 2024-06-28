package com.taxicardriver.network.apiservices

import com.taxicardriver.models.dtos.UserRegisterData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegisterApiService {
    @POST("new/register")
    fun registerUser(@Body userRegisterData: UserRegisterData): Call<ResponseBody>
}