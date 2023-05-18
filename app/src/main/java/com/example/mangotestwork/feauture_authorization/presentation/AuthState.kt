package com.example.mangotestwork.feauture_authorization.presentation


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    data class Authenticated(val accessToken: String, val userId: String) : AuthState()
    object RegistrationRequired : AuthState()
    object CodeSent : AuthState()
}