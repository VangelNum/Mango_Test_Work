package com.example.mangotestwork.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.core.data.model.UserProfileResponse
import com.example.mangotestwork.core.data.repository.CoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: CoreRepository
) : ViewModel() {
    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState: StateFlow<ProfileState?> = _profileState

    fun getUserProfile() {
        viewModelScope.launch {
            _loadingState.value = true
            val response = repository.getUserProfile()
            _loadingState.value = false

            if (response.isSuccessful) {
                val userProfileResponse = response.body()
                _profileState.value = userProfileResponse?.let { ProfileState.Success(it) }
            } else {
                _profileState.value = ProfileState.Error
            }
        }
    }
}

sealed class ProfileState {
    data class Success(val userProfile: UserProfileResponse) : ProfileState()
    object Error : ProfileState()
}