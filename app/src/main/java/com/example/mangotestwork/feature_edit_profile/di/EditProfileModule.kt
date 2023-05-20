package com.example.mangotestwork.feature_edit_profile.di

import com.example.mangotestwork.feature_edit_profile.data.api.EditProfileService
import com.example.mangotestwork.feature_edit_profile.data.repository.EditProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditProfileModule {
    @Provides
    @Singleton
    fun provideEditProfileService(retrofit: Retrofit): EditProfileService {
        return retrofit.create(EditProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideEditProfileRepository(editProfileService: EditProfileService): EditProfileRepository {
        return EditProfileRepository(editProfileService)
    }
}