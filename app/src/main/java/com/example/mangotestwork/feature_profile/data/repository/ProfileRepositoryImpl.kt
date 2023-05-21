package com.example.mangotestwork.feature_profile.data.repository

import com.example.mangotestwork.core.data.model.CacheProfileEntity
import com.example.mangotestwork.core.data.network.CacheProfileDao
import com.example.mangotestwork.feature_profile.data.api.ProfileService
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse
import com.example.mangotestwork.feature_profile.domain.repository.ProfileRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
    private val cacheProfileDao: CacheProfileDao
) : ProfileRepository {
    override suspend fun getUserProfile(): Response<UserProfileResponse> {
        val cachedUserProfile = cacheProfileDao.getUserProfile()
        if (cachedUserProfile != null) {
            return Response.success(
                UserProfileResponse(
                    avatar = cachedUserProfile.avatar,
                    phone = cachedUserProfile.phone,
                    nickname = cachedUserProfile.nickname,
                    city = cachedUserProfile.city,
                    birthday = cachedUserProfile.birthday,
                    zodiac = cachedUserProfile.zodiac,
                    about = cachedUserProfile.about
                )
            )
        }

        // Если кэшированных данных нет, выполняем запрос на сервер
        val response = profileService.getUserProfile()
        if (response.isSuccessful) {
            val userProfileResponse = response.body()
            userProfileResponse?.let {
                val userProfileEntity = CacheProfileEntity(
                    avatar = it.avatar,
                    phone = it.phone,
                    nickname = it.nickname,
                    city = it.city,
                    birthday = it.birthday,
                    zodiac = it.zodiac,
                    about = it.about
                )
                cacheProfileDao.insertUserProfile(userProfileEntity)
            }
        }
        return response
    }
}