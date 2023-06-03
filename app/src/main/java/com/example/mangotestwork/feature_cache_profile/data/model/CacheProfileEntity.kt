package com.example.mangotestwork.feature_cache_profile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mangotestwork.feature_edit_profile.data.model.Avatars
import com.google.gson.Gson
import org.json.JSONObject

@Entity(tableName = "user_profile")
@TypeConverters(AvatarsTypeConverter::class)
data class CacheProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val username: String? = null,
    val birthday: String? = null,
    val city: String? = null,
    val vk: String? = null,
    val instagram: String? = null,
    val status: String? = null,
    val avatar: String? = null,
    val last: String? = null,
    val online: Boolean? = null,
    val created: String?= null,
    val phone: String?= null,
    val completed_task: Int?= null,
    val avatars: Avatars? = null
)

class AvatarsTypeConverter {
    @TypeConverter
    fun fromAvatars(avatars: Avatars?): String? {
        return avatars?.let {
            val jsonObject = JSONObject()
            jsonObject.put("avatar", it.avatar)
            jsonObject.put("bigAvatar", it.bigAvatar)
            jsonObject.put("miniAvatar", it.miniAvatar)
            jsonObject.toString()
        }
    }

    @TypeConverter
    fun toAvatars(avatarsString: String?): Avatars? {
        return avatarsString?.let {
            val jsonObject = JSONObject(it)
            val avatar = jsonObject.getString("avatar")
            val bigAvatar = jsonObject.getString("bigAvatar")
            val miniAvatar = jsonObject.getString("miniAvatar")
            Avatars(avatar, bigAvatar, miniAvatar)
        }
    }
}