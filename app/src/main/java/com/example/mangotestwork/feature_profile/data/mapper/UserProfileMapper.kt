package com.example.mangotestwork.feature_profile.data.mapper

import com.example.mangotestwork.feature_cache_profile.data.model.CacheProfileEntity
import com.example.mangotestwork.feature_edit_profile.data.model.Avatars
import com.example.mangotestwork.feature_profile.data.model.ProfileResponse
import com.example.mangotestwork.feature_profile.data.model.UserProfileResponse

fun CacheProfileEntity.toUserProfile(): ProfileResponse {
    return ProfileResponse(
        UserProfileResponse(
            id = this.id,
            avatar = this.avatar,
            phone = this.phone,
            name = this.name,
            city = this.city,
            birthday = this.birthday,
            username = this.username,
            vk = this.vk,
            instagram = this.instagram,
            status = this.status,
            last = this.last,
            online = this.online,
            created = this.created,
            completedTask = this.completed_task,
            avatars = this.avatars
        )
    )
}




fun UserProfileResponse.toUserProfileEntity(): CacheProfileEntity {
    return CacheProfileEntity(
        id = this.id,
        avatar = this.avatar,
        phone = this.phone,
        name = this.name,
        city = this.city,
        birthday = this.birthday,
        username = this.username,
        vk = this.vk,
        instagram = this.instagram,
        status = this.status,
        last = this.last,
        online = this.online,
        created = this.created,
        completed_task = this.completedTask,
        avatars = this.avatars
    )
}