package com.example.mangotestwork.feauture_authorization.data.repository

import com.example.mangotestwork.feauture_authorization.data.api.AuthService
import com.example.mangotestwork.feauture_authorization.data.model.CheckAuthCodeRequest
import com.example.mangotestwork.feauture_authorization.data.model.CheckAuthCodeResponse
import com.example.mangotestwork.feauture_authorization.data.model.SendAuthCodeRequest
import com.example.mangotestwork.feauture_authorization.data.model.SendAuthCodeResponse
import com.example.mangotestwork.feauture_authorization.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun sendAuthCode(phone: String): Response<SendAuthCodeResponse> {
        val request = SendAuthCodeRequest(phone)
        return authService.sendAuthCode(request)
    }

    override suspend fun checkAuthCode(
        phone: String,
        code: String
    ): Response<CheckAuthCodeResponse> {
        val request = CheckAuthCodeRequest(phone, code)
        return authService.checkAuthCode(request)
    }
}