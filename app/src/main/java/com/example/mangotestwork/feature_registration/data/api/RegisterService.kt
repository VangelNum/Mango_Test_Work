package com.example.mangotestwork.feature_registration.data.api

import com.example.mangotestwork.feature_registration.data.model.RegisterUserRequest
import com.example.mangotestwork.feature_registration.data.model.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    // Запрос на регистрацию
    @POST("/api/v1/users/register/")
    suspend fun registerUser(@Body request: RegisterUserRequest): Response<RegisterUserResponse>
}