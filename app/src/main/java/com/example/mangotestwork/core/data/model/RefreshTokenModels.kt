package com.example.mangotestwork.core.data.model

import com.google.gson.annotations.SerializedName


data class RefreshTokenRequest(@SerializedName("refresh_token") val refreshToken: String)
data class RefreshTokenResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int
)