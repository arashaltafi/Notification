package com.arash.altafi.notification2.models

import com.google.gson.annotations.SerializedName

data class NotificationMessageModel(
    @SerializedName("user_avatar")
    val userAvatar: String? = null,
    @SerializedName("user_name")
    val userName: String? = null,
    @SerializedName("text")
    val text: String? = null
)