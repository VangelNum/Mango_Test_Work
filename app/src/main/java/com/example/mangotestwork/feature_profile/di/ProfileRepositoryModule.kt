package com.example.mangotestwork.feature_profile.di

import com.example.mangotestwork.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.mangotestwork.feature_profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoryModule {
    @Binds
    @Singleton
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}