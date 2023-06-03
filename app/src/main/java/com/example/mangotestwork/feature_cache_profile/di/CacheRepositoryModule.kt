package com.example.mangotestwork.feature_cache_profile.di

import com.example.mangotestwork.feature_cache_profile.data.repository.CacheProfileRepositoryImpl
import com.example.mangotestwork.feature_cache_profile.domain.repository.CacheProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CacheRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCacheRepository(
        cacheProfileRepositoryImpl: CacheProfileRepositoryImpl
    ): CacheProfileRepository
}