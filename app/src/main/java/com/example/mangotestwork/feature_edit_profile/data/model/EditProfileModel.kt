package com.example.mangotestwork.feature_edit_profile.data.model

data class UpdateProfileRequest(
    val name: String? = null,
    val username: String? = null,
    val birthday: String? = null,
    val city: String? = null,
    val vk: String? = null,
    val instagram: String? = null,
    val status: String? = null,
    val avatar: AvatarData? = null
)

data class AvatarData(val filename: String? = null, val base_64: String? = null)
data class UpdateProfileResponse(val avatars: Avatars)
data class Avatars(val avatar: String, val bigAvatar: String, val miniAvatar: String)