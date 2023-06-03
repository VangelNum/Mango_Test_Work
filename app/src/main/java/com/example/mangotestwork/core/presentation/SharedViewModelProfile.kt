package com.example.mangotestwork.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.feature_cache_profile.domain.repository.CacheProfileRepository
import com.example.mangotestwork.feature_edit_profile.data.mapper.toCacheProfileWithAvatar
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest
import com.example.mangotestwork.feature_edit_profile.data.repository.EditProfileRepository
import com.example.mangotestwork.feature_edit_profile.presentation.EditProfileState
import com.example.mangotestwork.feature_profile.data.mapper.toUserProfile
import com.example.mangotestwork.feature_profile.data.mapper.toUserProfileEntity
import com.example.mangotestwork.feature_profile.domain.repository.ProfileRepository
import com.example.mangotestwork.feature_profile.presentation.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModelProfile @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val editProfileRepository: EditProfileRepository,
    private val cacheProfileRepository: CacheProfileRepository
) : ViewModel() {
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _editProfileState = Channel<EditProfileState>()
    val editProfileState = _editProfileState.receiveAsFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            cacheProfileRepository.getCacheProfileData().collect { cache ->
                if (cache != null) {
                    _profileState.value = ProfileState.Success(cache.toUserProfile())
                } else {
                    val response = profileRepository.getUserProfile()
                    if (response.isSuccessful) {
                        val userProfileResponse = response.body()
                        userProfileResponse?.let {
                            _profileState.value = ProfileState.Success(userProfileResponse)
                            cacheProfileRepository.insertCacheProfileData(userProfileResponse.profileData.toUserProfileEntity())
                        }
                    } else {
                        _profileState.value = ProfileState.Error(response.message())
                    }
                }
            }
        }
    }

    fun updateProfile(id: Int, updateProfileRequest: UpdateProfileRequest) {
        viewModelScope.launch {
            _editProfileState.send(EditProfileState.Loading)
            val response = editProfileRepository.updateProfile(updateProfileRequest)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.avatars != null) {
                    val avatar = body.avatars.avatar
                    val bigAvatar = body.avatars.bigAvatar
                    val miniAvatar = body.avatars.miniAvatar
                    cacheProfileRepository.updateCacheProfileData(
                        updateProfileRequest.toCacheProfileWithAvatar(
                            id,
                            avatar,
                            bigAvatar,
                            miniAvatar
                        )
                    )
                } else {
                    cacheProfileRepository.updateCacheProfileWithoutAvatar(
                        id = id,
                        name = updateProfileRequest.name,
                        username = updateProfileRequest.username,
                        birthday = updateProfileRequest.birthday,
                        city = updateProfileRequest.city,
                        vk = updateProfileRequest.vk,
                        instagram = updateProfileRequest.instagram,
                    )
                }
                _editProfileState.send(EditProfileState.Success)
            } else {
                _editProfileState.send(EditProfileState.Error(response.message()))
            }
        }
    }


}

