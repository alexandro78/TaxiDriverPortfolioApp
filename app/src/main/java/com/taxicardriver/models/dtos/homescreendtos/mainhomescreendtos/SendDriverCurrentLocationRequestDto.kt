package com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos

data class SendDriverCurrentLocationRequestDto(
    val driverLocationLatitude: String,
    val driverLocationLongitude: String
)
