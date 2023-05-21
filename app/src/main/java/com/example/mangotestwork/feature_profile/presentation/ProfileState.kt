package com.example.mangotestwork.feature_profile.presentation

import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse

sealed class ProfileState {
    data class Success(val userProfile: UserProfileResponse) : ProfileState()
    object Loading : ProfileState()
    object Error : ProfileState()
    object Idle: ProfileState()
}