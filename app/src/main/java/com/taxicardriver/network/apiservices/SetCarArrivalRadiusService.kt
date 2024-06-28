package com.taxicardriver.network.apiservices

import com.taxicardriver.models.dtos.homescreendtos.setcararrivalradiusrequestdtos.SetCarArrivalRadiusRequestDto
import com.taxicardriver.models.dtos.homescreendtos.setcararrivalradiusrequestdtos.SetCarArrivalRadiusResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SetCarArrivalRadiusService {
    @POST("driver-home-screen/update-car-arrival-radius")
    fun setCarArrivalRadius(@Body reuestRadiusDto: SetCarArrivalRadiusRequestDto): Call<SetCarArrivalRadiusResponseDto>
}
