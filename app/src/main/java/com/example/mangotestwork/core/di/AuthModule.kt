package com.example.mangotestwork.core.di

import android.content.Context
import com.example.mangotestwork.core.data.api.CoreService
import com.example.mangotestwork.core.data.repository.CoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url.toString()

                // Проверяем, требуется ли добавление заголовка авторизации
                val requiresAuthorization = !originalUrl.contains("/api/v1/users/register/") &&
                        !originalUrl.contains("/api/v1/users/send-auth-code/") &&
                        !originalUrl.contains("/api/v1/users/check-auth-code/")

                // Если требуется авторизация, добавляем заголовок
                val modifiedRequest = if (requiresAuthorization) {
                    val accessToken = context.getSharedPreferences("access_token",Context.MODE_PRIVATE)
                    val authHeader = "Bearer $accessToken"
                    originalRequest.newBuilder()
                        .header("Authorization", authHeader)
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(client: OkHttpClient): CoreService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://plannerok.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(CoreService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(coreService: CoreService): CoreRepository {
        return CoreRepository(coreService)
    }

}