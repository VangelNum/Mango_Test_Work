package com.example.mangotestwork.feauture_authorization.data.model

import com.google.gson.annotations.SerializedName

data class SendAuthCodeRequest(val phone: String)
data class SendAuthCodeResponse(val success: Boolean)

data class CheckAuthCodeRequest(val phone: String, val code: String)
data class CheckAuthCodeResponse(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("is_user_exists")
    val userExists: Boolean
)
