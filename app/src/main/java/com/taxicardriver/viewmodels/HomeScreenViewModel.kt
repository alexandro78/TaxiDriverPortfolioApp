package com.taxicardriver.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos.SendDriverCurrentLocationRequestDto
import com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos.UserHomeScreenResponseDto
import com.taxicardriver.models.dtos.homescreendtos.setcararrivalradiusrequestdtos.SetCarArrivalRadiusRequestDto
import com.taxicardriver.models.dtos.homescreendtos.setcararrivalradiusrequestdtos.SetCarArrivalRadiusResponseDto
import com.taxicardriver.network.connectbuilders.socketbuilders.websocketclients.HomeScreenWebSocketClien
import com.taxicardriver.network.connectbuilders.httpbuilders.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel : ViewModel() {
    var responseStatus: Boolean? by mutableStateOf(null)
    var isLoadingScreenDialogVisible by mutableStateOf(true)
    var serverConnectProblem by mutableStateOf(false)
    var serverConnectProblemType by mutableStateOf("")

    var responseHttpCode by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    var userInstance: UserHomeScreenResponseDto? by mutableStateOf(null)
    var setCarArrivalRadius: Float? by mutableStateOf(null)

    var webSocketStatus by mutableStateOf("Disconnected")
    var serverResponseMessage by mutableStateOf("000")
    var isUnexpectedDisconnected by mutableStateOf(false)


    //////////////*** websocket connection features ***//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
// Creating a WebSocket client
    private val webSocketClient by lazy {
        HomeScreenWebSocketClien(
            url = "ws://192.168.0.187:8080/websocket",
            onMessageReceived = { message ->
                // Process the received message
                serverResponseMessage = message
            },
            onStatusChanged = { status ->
                webSocketStatus = status
                if (status.contains("Connected")) {
                    isUnexpectedDisconnected = false
                }
            },
            onDisconnected = { unexpected ->
                isUnexpectedDisconnected = unexpected
            }
        )
    }

    fun connect() {
        webSocketClient.connect()
    }

    fun sendMessage(message: SendDriverCurrentLocationRequestDto) {
        val json = Gson().toJson(message)  // Convert DTO to JSON
        webSocketClient.sendMessage(json)
    }

    fun disconnect() {
        webSocketClient.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        // Close WebSocket connection when ViewModel is destroyed
        webSocketClient.disconnect()
    }
    //////////////*** websocket connection features ends ***//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////



    //////////////*** http connection features ***//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
    fun loadHomeScreenUserData() {
        // Receive the token and send the request
        val token = RetrofitBuilder.getToken()
        if (token != null) {
            RetrofitBuilder.driverHomeScreenApiService.getHomeUserData()
                .enqueue(object : Callback<UserHomeScreenResponseDto> {
                    override fun onResponse(
                        call: Call<UserHomeScreenResponseDto>,
                        response: Response<UserHomeScreenResponseDto>
                    ) {
                        if (response.isSuccessful) {
                            responseStatus = true
                            userInstance = response.body()
                            responseHttpCode =
                                response.code().toString()
                            setCarArrivalRadius = userInstance?.driver?.carArrivalRadius
                            Log.d("LoginJwt", ">>>" + response.isSuccessful.toString())
                            Log.d(
                                "LoginJwt",
                                "Успішно $responseHttpCode, код: ${response.code()} $responseStatus"
                            )
//                            Log.d("LoginJwt", "UserInfo: $userInfo")
                        } else {
                            responseStatus = false
                            Log.e(
                                "LoginJwt",
                                "Error: $errorMessage ${response.code()} - ${response.message()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<UserHomeScreenResponseDto>, t: Throwable) {
                        responseStatus = false
                        serverConnectProblem = true
                        serverConnectProblemType = t.message.toString()
                        Log.e("LoginJwt", "Failure: $serverConnectProblemType")
                    }
                })
        } else {
            responseStatus = false
            errorMessage = "Error: Token is null or invalid"
            Log.e("LoginJwt", "Error: Token is null or invalid")
        }
    }
    //////////////////////////////////////////////////////////////////////////


    ////Updating the car arrival radius///////////////////////////////////////
    fun updateCarArrivalRadius(carArrivalRadius: Float) {
        val radiusRequestDto = SetCarArrivalRadiusRequestDto(carArrivalRadius)
        RetrofitBuilder.setCarArrivalRadiusService.setCarArrivalRadius(radiusRequestDto)
            .enqueue(object : Callback<SetCarArrivalRadiusResponseDto> {
                override fun onResponse(
                    call: Call<SetCarArrivalRadiusResponseDto>,
                    response: Response<SetCarArrivalRadiusResponseDto>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val updatedRadius = response.body()!!.carArrivalRadius
                        setCarArrivalRadius = updatedRadius
                        Log.d("UpdateRadius", "Successfully updated: $updatedRadius")
                    } else {
                        Log.e("UpdateRadius", "Server error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SetCarArrivalRadiusResponseDto>, t: Throwable) {
                    Log.e("UpdateRadius", "Failure: ${t.message}")
                }
            })
    }
    //////////////*** http connection features ends ***//////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
}