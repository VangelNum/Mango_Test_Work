package com.example.mangotestwork.feature_registration.data.repository

import com.example.mangotestwork.feature_registration.data.api.RegisterService
import com.example.mangotestwork.feature_registration.data.model.RegisterUserRequest
import com.example.mangotestwork.feature_registration.data.model.RegisterUserResponse
import com.example.mangotestwork.feature_registration.domain.repository.RegisterRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService
): RegisterRepository {
    override suspend fun registerUser(
        phone: String,
        name: String,
        username: String
    ): Response<RegisterUserResponse> {
        val request = RegisterUserRequest(phone, name, username)
        return registerService.registerUser(request)
    }
}