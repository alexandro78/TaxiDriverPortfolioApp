package com.taxicardriver.network.apiservices

import com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos.UserHomeScreenResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface DriverHomeScreenApiService {
    @GET("driver-home-screen/userinfo")
    fun getHomeUserData(): Call<UserHomeScreenResponseDto>
}