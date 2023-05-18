package com.example.mangotestwork.core.data.api

import com.example.mangotestwork.core.data.model.RefreshTokenRequest
import com.example.mangotestwork.core.data.model.RefreshTokenResponse
import com.example.mangotestwork.core.data.model.UpdateProfileRequest
import com.example.mangotestwork.core.data.model.UpdateProfileResponse
import com.example.mangotestwork.core.data.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CoreService {

    // Запрос на обновление профиля пользователя
    @PUT("/api/v1/users/me/")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>

    // Запрос на получение данных пользователя
    @GET("/api/v1/users/me/")
    suspend fun getUserProfile(): Response<UserProfileResponse>

    // Запрос на обновление access token с помощью refresh token
    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
}