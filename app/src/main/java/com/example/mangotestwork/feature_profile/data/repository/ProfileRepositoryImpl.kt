package com.example.mangotestwork.feature_profile.data.repository

import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileResponse
import com.example.mangotestwork.feature_profile.data.api.ProfileService
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import com.example.mangotestwork.feature_profile.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
) : ProfileRepository {
    override suspend fun getUserProfile(): Response<ProfileResponse> {
        return profileService.getUserProfile()
    }

    override suspend fun updateUserProfile(
        userProfile: UpdateProfileRequest
    ): Response<UpdateProfileResponse> {
        return profileService.updateProfile(userProfile)
    }
}