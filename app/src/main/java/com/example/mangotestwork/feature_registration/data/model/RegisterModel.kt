package com.example.mangotestwork.feature_registration.data.model

data class RegisterUserRequest(val phone: String, val name: String, val username: String)
data class RegisterUserResponse(
    val refreshToken: String,
    val accessToken: String,
    val userId: String
)