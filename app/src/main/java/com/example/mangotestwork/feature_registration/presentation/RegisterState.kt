package com.example.mangotestwork.feature_registration.presentation

sealed class RegisterState {
    data class Success(val refreshToken: String?, val accessToken: String, val userId: String) : RegisterState()
    object Loading : RegisterState()
    data class Error(val message: String) : RegisterState()
    object Idle : RegisterState()
}