package com.example.mangotestwork.feauture_authorization.domain.repository

import com.example.mangotestwork.feauture_authorization.data.model.CheckAuthCodeResponse
import com.example.mangotestwork.feauture_authorization.data.model.SendAuthCodeResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun sendAuthCode(phone: String): Response<SendAuthCodeResponse>
    suspend fun checkAuthCode(phone: String, code: String): Response<CheckAuthCodeResponse>
}