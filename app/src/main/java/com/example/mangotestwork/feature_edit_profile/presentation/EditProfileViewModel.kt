package com.example.mangotestwork.feature_edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.core.data.model.AvatarData
import com.example.mangotestwork.core.data.repository.CoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: CoreRepository
): ViewModel() {
    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _editProfileState = MutableStateFlow<EditProfileState?>(null)
    val editProfileState: StateFlow<EditProfileState?> = _editProfileState

    fun updateProfile(name: String, city: String, birthday: String, about: String, avatar: AvatarData) {
        viewModelScope.launch {
            _loadingState.value = true
            val response = repository.updateProfile(name, city, birthday, about, avatar)
            _loadingState.value = false

            if (response.isSuccessful) {
                _editProfileState.value = EditProfileState.Success
            } else {
                _editProfileState.value = EditProfileState.Error
            }
        }
    }
}

sealed class EditProfileState {
    object Success : EditProfileState()
    object Error : EditProfileState()
}