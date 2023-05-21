package com.example.mangotestwork.core.data.repository

import com.example.mangotestwork.core.data.model.CacheProfileEntity
import com.example.mangotestwork.core.data.network.CacheProfileDao
import com.example.mangotestwork.core.domain.repository.CacheProfileRepository
import javax.inject.Inject

class CacheProfileRepositoryImpl @Inject constructor(
    private val cacheProfileDao: CacheProfileDao
) : CacheProfileRepository {
    override suspend fun getCacheProfileData(): CacheProfileEntity? {
        return cacheProfileDao.getUserProfile()
    }

    override suspend fun insertCacheProfileData(userProfileEntity: CacheProfileEntity) {
        cacheProfileDao.insertUserProfile(userProfileEntity)
    }

    override suspend fun updateCacheProfileData(userProfileEntity: CacheProfileEntity) {
        cacheProfileDao.updateUserProfile(userProfileEntity)
    }

}