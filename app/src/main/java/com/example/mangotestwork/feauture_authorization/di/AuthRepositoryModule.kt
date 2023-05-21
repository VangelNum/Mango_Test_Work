package com.example.mangotestwork.feauture_authorization.di

import com.example.mangotestwork.feauture_authorization.data.repository.AuthRepositoryImpl
import com.example.mangotestwork.feauture_authorization.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthRepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}