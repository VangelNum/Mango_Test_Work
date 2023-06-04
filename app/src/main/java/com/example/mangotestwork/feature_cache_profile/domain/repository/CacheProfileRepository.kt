package com.example.mangotestwork.feature_cache_profile.domain.repository

import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity
import kotlinx.coroutines.flow.Flow

interface CacheProfileRepository {
    fun getCacheProfileData(): Flow<CacheProfileEntity?>
    suspend fun insertCacheProfileData(userProfileEntity: CacheProfileEntity)
    suspend fun updateCacheProfileData(userProfileEntity: CacheProfileEntity)
    suspend fun updateCacheProfileWithoutAvatar(
        id: Int,
        name: String?,
        username: String?,
        birthday: String?,
        city: String?,
        vk: String?,
        instagram: String?
    )
    suspend fun deleteCacheProfileData()
}