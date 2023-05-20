package com.example.mangotestwork.feature_profile.data.api

import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileService {
    @GET("/api/v1/users/me/")
    suspend fun getUserProfile(): Response<UserProfileResponse>
}
