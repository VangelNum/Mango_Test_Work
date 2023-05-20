package com.example.mangotestwork.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.core.data.repository.CoreRepository
import com.example.mangotestwork.feature_profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val response = repository.getUserProfile()
            if (response.isSuccessful) {
                val userProfileResponse = response.body()
                userProfileResponse?.let {
                    _profileState.value = ProfileState.Success(userProfileResponse)
                }
            } else {
                _profileState.value = ProfileState.Error
            }
        }
    }
}

