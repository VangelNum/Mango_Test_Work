package com.example.mangotestwork.feature_edit_profile.data.mapper

import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity
import com.example.mangotestwork.feature_edit_profile.data.model.Avatars
import com.example.mangotestwork.feature_edit_profile.data.model.UpdateProfileRequest

fun UpdateProfileRequest.toCacheProfileWithAvatar(
    id: Int,
    avatar: String,
    bigAvatar: String,
    minAvatar: String
): CacheProfileEntity {
    return CacheProfileEntity(
        id = id,
        name = this.name,
        username = this.username,
        birthday = this.birthday,
        city = this.city,
        vk = this.vk,
        instagram = this.instagram,
        status = this.status,
        avatars = Avatars(avatar, bigAvatar, minAvatar)
    )
}


fun UpdateProfileRequest.toCacheProfileWithoutAvatar(id: Int): CacheProfileEntity {
    return CacheProfileEntity(
        id = id,
        name = this.name,
        username = this.username,
        birthday = this.birthday,
        city = this.city,
        vk = this.vk,
        instagram = this.instagram,
        status = this.status,
    )
}