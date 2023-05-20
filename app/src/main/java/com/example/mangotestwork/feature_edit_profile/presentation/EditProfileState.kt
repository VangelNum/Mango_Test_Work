package com.example.mangotestwork.feature_edit_profile.presentation

sealed class EditProfileState {
    object Success : EditProfileState()
    object Error : EditProfileState()
    object Loading: EditProfileState()
    object Idle: EditProfileState()
}