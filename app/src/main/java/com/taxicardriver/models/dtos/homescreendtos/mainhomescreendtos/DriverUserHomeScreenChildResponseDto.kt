package com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos

data class DriverUserHomeScreenChildResponseDto(
    val availableStatus: Boolean,
    val carArrivalRadius: Float,
    val driverTimestamp: String
)