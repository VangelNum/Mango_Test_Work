package com.example.mangotestwork.feature_edit_profile.data.repository

import com.example.mangotestwork.feature_edit_profile.data.api.EditProfileService
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileResponse
import retrofit2.Response

class EditProfileRepository(
    private val editProfileService: EditProfileService
) {
    suspend fun updateProfile(
        userProfileRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse> {
        return editProfileService.updateProfile(userProfileRequest)
    }

}