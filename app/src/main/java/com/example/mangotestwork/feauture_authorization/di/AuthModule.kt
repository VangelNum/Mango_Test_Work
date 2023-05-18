package com.example.mangotestwork.feauture_authorization.di

import com.example.mangotestwork.core.common.Constants.BASE_URL
import com.example.mangotestwork.feauture_authorization.data.api.AuthService
import com.example.mangotestwork.feauture_authorization.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(client: OkHttpClient): AuthService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService) : AuthRepository {
        return AuthRepository(authService)
    }
}