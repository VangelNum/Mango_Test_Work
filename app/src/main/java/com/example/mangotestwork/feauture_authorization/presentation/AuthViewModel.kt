package com.example.mangotestwork.feauture_authorization.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.feauture_authorization.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = authRepository.sendAuthCode(phone)
            if (response.isSuccessful) {
                _authState.value = AuthState.CodeSent
            } else {
                _authState.value = AuthState.Error(response.message())
            }
        }
    }

    fun checkAuthCode(phone: String, code: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.checkAuthCode(phone, code)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val refreshToken = responseBody.refreshToken
                        val accessToken = responseBody.accessToken
                        val userId = responseBody.userId
                        val isUserExists = responseBody.userExists

                        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("refreshToken", refreshToken)
                        editor.putString("accessToken", accessToken)
                        editor.apply()
                        if (isUserExists) {
                            _authState.value = AuthState.Authenticated(accessToken, userId)
                        } else {
                            _authState.value = AuthState.RegistrationRequired
                        }
                    } else {
                        _authState.value = AuthState.Error("Invalid response body")
                    }
                } else {
                    _authState.value = AuthState.Error("Request failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
