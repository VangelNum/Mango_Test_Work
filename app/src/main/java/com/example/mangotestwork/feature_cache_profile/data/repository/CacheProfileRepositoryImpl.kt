package com.example.mangotestwork.feature_cache_profile.data.repository

import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity
import com.example.mangotestwork.feature_cache_profile.data.network.CacheProfileDao
import com.example.mangotestwork.feature_cache_profile.domain.repository.CacheProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheProfileRepositoryImpl @Inject constructor(
    private val cacheProfileDao: CacheProfileDao
) : CacheProfileRepository {
    override fun getCacheProfileData(): Flow<CacheProfileEntity?> = flow {
        try {
            cacheProfileDao.getUserProfile().collect {
                emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }
    }

    override suspend fun insertCacheProfileData(userProfileEntity: CacheProfileEntity) {
        cacheProfileDao.insertUserProfile(userProfileEntity)
    }

    override suspend fun updateCacheProfileData(userProfileEntity: CacheProfileEntity) {
        cacheProfileDao.updateUserProfile(userProfileEntity)
    }

    override suspend fun updateCacheProfileWithoutAvatar(
        id: Int,
        name: String?,
        username: String?,
        birthday: String?,
        city: String?,
        vk: String?,
        instagram: String?
    ) {
        cacheProfileDao.updateUserProfile(id, name, username, birthday, city, vk, instagram)
    }

    override suspend fun deleteCacheProfileData() {
        cacheProfileDao.deleteUserProfile()
    }

}