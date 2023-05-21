package com.example.mangotestwork.feature_registration.domain.repository

import com.example.mangotestwork.feature_registration.data.model.RegisterUserResponse
import retrofit2.Response

interface RegisterRepository {
    suspend fun registerUser(
        phone: String,
        name: String,
        username: String
    ): Response<RegisterUserResponse>
}