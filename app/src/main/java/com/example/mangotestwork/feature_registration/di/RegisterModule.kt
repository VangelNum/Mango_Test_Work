package com.example.mangotestwork.feature_registration.di

import com.example.mangotestwork.core.common.Constants.BASE_URL
import com.example.mangotestwork.core.data.api.CoreService
import com.example.mangotestwork.feature_registration.data.api.RegisterService
import com.example.mangotestwork.feature_registration.data.repository.RegisterRepository
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
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterService(client: OkHttpClient): RegisterService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(RegisterService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerService: RegisterService) : RegisterRepository {
        return RegisterRepository(registerService)
    }

}