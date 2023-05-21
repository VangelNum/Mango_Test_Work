package com.example.mangotestwork.feature_profile.domain.repository

import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import retrofit2.Response

interface ProfileRepository {
    suspend fun getUserProfile(): Response<UserProfileResponse>
}