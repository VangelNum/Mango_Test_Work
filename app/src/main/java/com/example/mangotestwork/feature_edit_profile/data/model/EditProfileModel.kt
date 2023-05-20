package com.example.mangotestwork.feature_edit_profile.data.model

import com.example.mangotestwork.feature_profile.data.model.AvatarData

data class UpdateProfileRequest(
    val name: String,
    val city: String,
    val birthday: String,
    val about: String,
    val avatar: AvatarData
)

data class UpdateProfileResponse(val success: Boolean)
