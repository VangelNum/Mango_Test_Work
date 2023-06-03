package com.example.mangotestwork.feature_cache_profile.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity

@Database(entities = [CacheProfileEntity::class], version = 2)
abstract class CacheProfileDB : RoomDatabase() {
    abstract fun userProfileDao(): CacheProfileDao
}