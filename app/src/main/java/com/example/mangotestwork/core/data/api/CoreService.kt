package com.example.mangotestwork.core.data.api

import com.example.mangotestwork.core.data.model.RefreshTokenRequest
import com.example.mangotestwork.core.data.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CoreService {
    // Запрос на обновление access token с помощью refresh token
    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
}