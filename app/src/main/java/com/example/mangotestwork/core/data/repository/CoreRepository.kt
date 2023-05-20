package com.example.mangotestwork.core.data.repository

import com.example.mangotestwork.core.data.api.CoreService
import com.example.mangotestwork.core.data.model.RefreshTokenRequest
import com.example.mangotestwork.core.data.model.RefreshTokenResponse
import retrofit2.Response

class CoreRepository(private val coreService: CoreService) {

    suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse> {
        val request = RefreshTokenRequest(refreshToken)
        return coreService.refreshToken(request)
    }
}