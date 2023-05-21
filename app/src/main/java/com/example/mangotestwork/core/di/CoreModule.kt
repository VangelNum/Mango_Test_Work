package com.example.mangotestwork.core.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mangotestwork.core.common.Constants.BASE_URL
import com.example.mangotestwork.core.data.api.RefreshTokenService
import com.example.mangotestwork.core.data.model.RefreshTokenRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
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
                    val sharedPreferences =
                        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val accessToken = sharedPreferences.getString("accessToken", "")
                    val authHeader = "Bearer $accessToken"
                    originalRequest.newBuilder()
                        .header("Authorization", authHeader)
                        .build()
                } else {
                    originalRequest
                }
                var response: Response? = null

                try {
                    response = chain.proceed(modifiedRequest)
                    if (response.code == 401 || response.code == 500) {
                        val sharedPreferences =
                            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val refreshToken =
                            sharedPreferences.getString("refreshToken", "empty_refresh_token")
                        val accessToken =
                            sharedPreferences.getString("accessToken", "empty_access_token")
                        val newToken =
                            refreshAccessToken(refreshToken, accessToken, sharedPreferences)
                        Log.d("token new access", newToken.toString())

                        // Повторяем неудавшийся запрос с новым токеном
                        val newModifiedRequest = modifiedRequest.newBuilder()
                            .header("Authorization", "Bearer $newToken")
                            .build()
                        response.close()
                        response = chain.proceed(newModifiedRequest)
                    }
                } catch (e: IOException) {

                }

                response ?: throw IllegalStateException("Response is null")
            }
            .build()
    }

    private fun refreshAccessToken(
        refreshToken: String?,
        accessToken: String?,
        sharedPreferences: SharedPreferences
    ): String? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(RefreshTokenService::class.java)
        val refreshTokenRequest = RefreshTokenRequest(refreshToken ?: "empty_token")
        try {
            Log.d("token access", accessToken.toString())
            val accessTokenOld = "Bearer $accessToken"
            val response = runBlocking {
                apiService.refreshToken(accessTokenOld, refreshTokenRequest)
            }
            Log.d("token body", response.toString())
            if (response.isSuccessful) {
                val refreshTokenResponse = response.body()
                sharedPreferences.edit().putString("accessToken", refreshTokenResponse?.accessToken)
                    .apply()
                sharedPreferences.edit()
                    .putString("refreshToken", refreshTokenResponse?.refreshToken).apply()
                return refreshTokenResponse?.accessToken
            } else {
                return null
            }
        } catch (e: IOException) {
            return null
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoreService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java)
    }


}


