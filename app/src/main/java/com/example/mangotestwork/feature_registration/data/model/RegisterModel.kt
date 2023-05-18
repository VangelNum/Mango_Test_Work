package com.example.mangotestwork.feature_registration.data.model

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(val phone: String, val name: String, val username: String)
data class RegisterUserResponse(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("user_id")
    val userId: String
)