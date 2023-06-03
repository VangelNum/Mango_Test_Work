package com.example.mangotestwork.feature_cache_profile.di

import android.content.Context
import androidx.room.Room
import com.example.mangotestwork.feature_cache_profile.data.network.CacheProfileDB
import com.example.mangotestwork.feature_cache_profile.data.network.CacheProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CacheProfileModule {
    @Provides
    @Singleton
    fun provideCacheProfileDB(@ApplicationContext context: Context): CacheProfileDB {
        synchronized(this) {
            return Room.databaseBuilder(
                context,
                CacheProfileDB::class.java,
                "cache_profile_db"
            ).build()
        }
    }

    @Provides
    @Singleton
    fun provideCacheUserProfileDao(database: CacheProfileDB): CacheProfileDao {
        return database.userProfileDao()
    }

}