package com.example.mangotestwork.feature_registration.di

import com.example.mangotestwork.feature_registration.data.api.RegisterService
import com.example.mangotestwork.feature_registration.data.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterService(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerService: RegisterService): RegisterRepository {
        return RegisterRepository(registerService)
    }

}