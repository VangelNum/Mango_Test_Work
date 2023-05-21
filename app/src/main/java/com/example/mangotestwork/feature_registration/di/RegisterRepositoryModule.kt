package com.example.mangotestwork.feature_registration.di

import com.example.mangotestwork.feature_registration.data.repository.RegisterRepositoryImpl
import com.example.mangotestwork.feature_registration.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository
}