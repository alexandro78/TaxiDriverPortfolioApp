package com.taxicardriver.models.dtos.homescreendtos.mainhomescreendtos

data class UserHomeScreenResponseDto(
    val firstName: String?,
    val lastName: String?,
    val phone: String,
    val roles: List<String>,
    val rating: Float,
    val id: Int,
    val email: String,
    val dSettings: DSettingsUserHomeScreenChildResponseDto?,
    val driver: DriverUserHomeScreenChildResponseDto?
)
