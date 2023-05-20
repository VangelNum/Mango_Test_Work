package com.example.mangotestwork.feature_profile.data.repository

import com.example.mangotestwork.feature_profile.data.api.ProfileService
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import retrofit2.Response

class ProfileRepository(private val profileService: ProfileService) {
    suspend fun getUserProfile(): Response<UserProfileResponse> {
        return profileService.getUserProfile()
    }
}