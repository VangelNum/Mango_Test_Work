package com.example.mangotestwork.feature_edit_profile.presentation

sealed class EditProfileState {
    object Success : EditProfileState()
    data class Error(val errorMessage: String) : EditProfileState()
    object Loading : EditProfileState()
    object Idle : EditProfileState()
}