package com.example.mangotestwork.feature_edit_profile.data.repository

import com.example.mangotestwork.feature_edit_profile.data.api.EditProfileService
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileResponse
import com.example.mangotestwork.feature_profile.data.model.AvatarData
import retrofit2.Response

class EditProfileRepository(private val editProfileService: EditProfileService) {
    suspend fun updateProfile(
        name: String,
        city: String,
        birthday: String,
        about: String,
        avatar: AvatarData
    ): Response<UpdateProfileResponse> {
        val request = UpdateProfileRequest(name, city, birthday, about, avatar)
        return editProfileService.updateProfile(request)
    }
}