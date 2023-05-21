package com.example.mangotestwork.feature_profile.data.model


data class AvatarData(val fileName: String, val base64Data: String)

data class UserProfileResponse(
    val avatar: String?,
    val phone: String?,
    val nickname: String?,
    val city: String?,
    val birthday: String?,
    val zodiac: String?,
    val about: String?
)