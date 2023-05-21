package com.example.mangotestwork.core.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangotestwork.core.data.model.CacheProfileEntity

@Database(entities = [CacheProfileEntity::class], version = 2)
abstract class CacheProfileDB : RoomDatabase() {
    abstract fun userProfileDao(): CacheProfileDao
}