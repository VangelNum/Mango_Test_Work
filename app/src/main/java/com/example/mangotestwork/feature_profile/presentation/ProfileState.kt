package com.example.mangotestwork.feature_profile.presentation

import com.example.mangotestwork.feature_profile.data.model.ProfileResponse

sealed class ProfileState {
    data class Success(val userProfile: ProfileResponse) : ProfileState()
    object Loading : ProfileState()
    data class Error(val errorMessage: String) : ProfileState()
    object Idle : ProfileState()
}