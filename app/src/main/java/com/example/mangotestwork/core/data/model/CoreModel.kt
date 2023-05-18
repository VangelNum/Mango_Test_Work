package com.example.mangotestwork.core.data.model



data class UpdateProfileRequest(
    val name: String,
    val city: String,
    val birthday: String,
    val about: String,
    val avatar: AvatarData
)

data class UpdateProfileResponse(val success: Boolean)

data class AvatarData(val fileName: String, val base64Data: String)

data class UserProfileResponse(
    val avatar: String,
    val phone: String,
    val nickname: String,
    val city: String,
    val birthday: String,
    val zodiac: String,
    val about: String
)

data class RefreshTokenRequest(val refreshToken: String)
data class RefreshTokenResponse(val accessToken: String)