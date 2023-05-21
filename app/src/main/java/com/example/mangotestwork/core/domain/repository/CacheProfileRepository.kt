package com.example.mangotestwork.core.domain.repository

import com.example.mangotestwork.core.data.model.CacheProfileEntity

interface CacheProfileRepository {
    suspend fun getCacheProfileData(): CacheProfileEntity?
    suspend fun insertCacheProfileData(userProfileEntity: CacheProfileEntity)
    suspend fun updateCacheProfileData(userProfileEntity: CacheProfileEntity)
}