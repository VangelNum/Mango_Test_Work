package com.example.mangotestwork.core.data.model


data class RefreshTokenRequest(val refreshToken: String)
data class RefreshTokenResponse(val accessToken: String)