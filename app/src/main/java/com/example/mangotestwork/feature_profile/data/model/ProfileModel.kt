package com.example.mangotestwork.feature_profile.data.model

import com.example.mangotestwork.feature_edit_profile.data.model.AvatarData
import com.example.mangotestwork.feature_edit_profile.data.model.Avatars
import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("profile_data")
    val profileData: UserProfileResponse
)

data class UserProfileResponse(
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val id: Int,
    val last: String?,
    val online: Boolean?,
    val created: String?,
    val phone: String?,
    @SerializedName("completed_task")
    val completedTask: Int?,
    val avatars: Avatars?
)



