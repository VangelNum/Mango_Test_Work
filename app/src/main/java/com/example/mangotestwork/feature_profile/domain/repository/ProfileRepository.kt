package com.example.mangotestwork.feature_profile.domain.repository

import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileResponse
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import retrofit2.Response

interface ProfileRepository {
    suspend fun getUserProfile(): Response<ProfileResponse>
    suspend fun updateUserProfile(
        userProfile: UpdateProfileRequest
    ): Response<UpdateProfileResponse>
}