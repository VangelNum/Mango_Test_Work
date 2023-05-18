package com.example.mangotestwork.core.data.repository

import com.example.mangotestwork.core.data.api.CoreService
import com.example.mangotestwork.core.data.model.AvatarData
import com.example.mangotestwork.core.data.model.RefreshTokenRequest
import com.example.mangotestwork.core.data.model.RefreshTokenResponse
import com.example.mangotestwork.core.data.model.UpdateProfileRequest
import com.example.mangotestwork.core.data.model.UpdateProfileResponse
import com.example.mangotestwork.core.data.model.UserProfileResponse
import retrofit2.Response

class CoreRepository(private val coreService: CoreService) {


    suspend fun updateProfile(
        name: String,
        city: String,
        birthday: String,
        about: String,
        avatar: AvatarData
    ): Response<UpdateProfileResponse> {
        val request = UpdateProfileRequest(name, city, birthday, about, avatar)
        return coreService.updateProfile(request)
    }

    suspend fun getUserProfile(): Response<UserProfileResponse> {
        return coreService.getUserProfile()
    }

    suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse> {
        val request = RefreshTokenRequest(refreshToken)
        return coreService.refreshToken(request)
    }
}