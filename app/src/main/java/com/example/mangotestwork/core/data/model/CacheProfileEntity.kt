package com.example.mangotestwork.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class CacheProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avatar: String?,
    val phone: String?,
    val nickname: String?,
    val city: String?,
    val birthday: String?,
    val zodiac: String?,
    val about: String?
)