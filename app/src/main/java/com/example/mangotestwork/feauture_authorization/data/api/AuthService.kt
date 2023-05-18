package com.example.mangotestwork.feauture_authorization.data.api

import com.example.mangotestwork.feauture_authorization.data.model.CheckAuthCodeRequest
import com.example.mangotestwork.feauture_authorization.data.model.CheckAuthCodeResponse
import com.example.mangotestwork.feauture_authorization.data.model.SendAuthCodeRequest
import com.example.mangotestwork.feauture_authorization.data.model.SendAuthCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequest): Response<SendAuthCodeResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequest): Response<CheckAuthCodeResponse>
}