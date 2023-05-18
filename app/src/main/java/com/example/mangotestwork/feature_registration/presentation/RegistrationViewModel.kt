package com.example.mangotestwork.feature_registration.presentation

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
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState?>(null)
    val registerState: StateFlow<RegisterState?> = _registerState.asStateFlow()

    fun registerUser(phone: String, name: String, username: String) {
        viewModelScope.launch {
            _loadingState.value = true
            val response = registerRepository.registerUser(phone, name, username)
            _loadingState.value = false

            if (response.isSuccessful) {
                val registerUserResponse = response.body()
                _registerState.value = RegisterState.Success(registerUserResponse?.accessToken)
            } else {
                _registerState.value = RegisterState.Error
            }
        }
    }
}

sealed class RegisterState {
    data class Success(val accessToken: String?) : RegisterState()
    object Error : RegisterState()
}