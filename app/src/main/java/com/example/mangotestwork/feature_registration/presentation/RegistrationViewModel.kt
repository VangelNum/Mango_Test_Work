package com.example.mangotestwork.feature_registration.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.feature_registration.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun registerUser(phone: String, name: String, username: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = registerRepository.registerUser(phone, name, username)
                if (response.isSuccessful) {
                    val registerUserResponse = response.body()
                    if (registerUserResponse != null) {
                        _registerState.value = RegisterState.Success(
                            refreshToken = registerUserResponse.refreshToken,
                            accessToken = registerUserResponse.accessToken.toString(),
                            userId = registerUserResponse.userId
                        )
                    }
                } else {
                    _registerState.value = RegisterState.Error(response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("tag", "exception: ${e.message.toString()}")
                _registerState.value = RegisterState.Error(e.message.toString())
            }
        }
    }
}

