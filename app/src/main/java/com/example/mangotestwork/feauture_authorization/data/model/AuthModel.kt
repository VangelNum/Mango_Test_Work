package com.example.mangotestwork.feauture_authorization.data.model

data class SendAuthCodeRequest(val phone: String)
data class SendAuthCodeResponse(val success: Boolean)

data class CheckAuthCodeRequest(val phone: String, val code: String)
data class CheckAuthCodeResponse(
    val refreshToken: String,
    val accessToken: String,
    val userId: String,
    val userExists: Boolean
)
