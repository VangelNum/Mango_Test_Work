package com.example.mangotestwork.core.data.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mangotestwork.core.data.model.CacheProfileEntity

@Dao
interface CacheProfileDao {
    @Query("SELECT * FROM user_profile")
    suspend fun getUserProfile(): CacheProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: CacheProfileEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserProfile(userProfile: CacheProfileEntity)
}