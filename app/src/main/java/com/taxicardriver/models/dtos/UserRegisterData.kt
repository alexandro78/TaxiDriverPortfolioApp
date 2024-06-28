package com.taxicardriver.models.dtos

data class UserRegisterData(
    val email: String,
    val password: String,
    val firstName: String,
    val phone: String
)
