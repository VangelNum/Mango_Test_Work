package com.example.mangotestwork.feature_edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.feature_edit_profile.data.repository.EditProfileRepository
import com.example.mangotestwork.feature_profile.data.model.AvatarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: EditProfileRepository
) : ViewModel() {

    private val _editProfileState = MutableStateFlow<EditProfileState>(EditProfileState.Idle)
    val editProfileState: StateFlow<EditProfileState> = _editProfileState.asStateFlow()

    fun updateProfile(
        name: String,
        city: String,
        birthday: String,
        about: String,
        avatar: AvatarData
    ) {
        viewModelScope.launch {
            _editProfileState.value = EditProfileState.Loading
            val response = repository.updateProfile(name, city, birthday, about, avatar)
            if (response.isSuccessful) {
                _editProfileState.value = EditProfileState.Success
            } else {
                _editProfileState.value = EditProfileState.Error
            }
        }
    }
}

