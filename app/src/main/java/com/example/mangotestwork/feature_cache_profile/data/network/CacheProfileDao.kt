package com.example.mangotestwork.feature_cache_profile.data.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheProfileDao {
    @Query("SELECT * FROM user_profile")
    fun getUserProfile(): Flow<CacheProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: CacheProfileEntity)

    @Update
    suspend fun updateUserProfile(userProfile: CacheProfileEntity)


    @Query("UPDATE user_profile SET name = :name, username = :username, birthday = :birthday, city = :city, vk = :vk, instagram = :instagram WHERE id = :id")
    suspend fun updateUserProfile(
        id: Int,
        name: String?,
        username: String?,
        birthday: String?,
        city: String?,
        vk: String?,
        instagram: String?
    )

    @Query("DELETE FROM user_profile")
    suspend fun deleteUserProfile()
}