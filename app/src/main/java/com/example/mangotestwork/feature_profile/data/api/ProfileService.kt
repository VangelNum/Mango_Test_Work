package com.example.mangotestwork.feature_profile.data.api

import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileResponse
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileService {
    @GET("/api/v1/users/me/")
    suspend fun getUserProfile(): Response<ProfileResponse>

    @PUT("/api/v1/users/me/")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>
}
