package com.example.mangotestwork.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangotestwork.core.data.model.CacheProfileEntity
import com.example.mangotestwork.core.domain.repository.CacheProfileRepository
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import com.example.mangotestwork.feature_profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val cacheProfileRepository: CacheProfileRepository
) : ViewModel() {
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            val storedUserProfile = cacheProfileRepository.getCacheProfileData()
            if (storedUserProfile != null) {
                _profileState.value =
                    ProfileState.Success(storedUserProfile.toUserProfileResponse())
            } else {
                val response = profileRepository.getUserProfile()
                if (response.isSuccessful) {
                    val userProfileResponse = response.body()
                    userProfileResponse?.let {
                        _profileState.value = ProfileState.Success(userProfileResponse)
                        cacheProfileRepository.insertCacheProfileData(userProfileResponse.toUserProfileEntity())
                    }
                } else {
                    _profileState.value = ProfileState.Error
                }
            }
        }
    }
}

fun CacheProfileEntity.toUserProfileResponse(): UserProfileResponse {
    return UserProfileResponse(
        avatar = this.avatar,
        phone = this.phone,
        nickname = this.nickname,
        city = this.city,
        birthday = this.birthday,
        zodiac = this.zodiac,
        about = this.about
    )
}

fun UserProfileResponse.toUserProfileEntity(): CacheProfileEntity {
    return CacheProfileEntity(
        avatar = this.avatar,
        phone = this.phone,
        nickname = this.nickname,
        city = this.city,
        birthday = this.birthday,
        zodiac = this.zodiac,
        about = this.about
    )
}
