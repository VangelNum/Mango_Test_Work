package com.example.mangotestwork.feature_profile.di

import com.example.mangotestwork.core.data.network.CacheProfileDao
import com.example.mangotestwork.feature_profile.data.api.ProfileService
import com.example.mangotestwork.feature_profile.data.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }
}