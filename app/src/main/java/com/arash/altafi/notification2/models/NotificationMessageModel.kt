package com.arash.altafi.notification2.models

import com.google.gson.annotations.SerializedName

data class NotificationMessageModel(

    @field:SerializedName("username")
    val username: List<String>,

    @field:SerializedName("message")
    val message: List<String>,

    @field:SerializedName("time")
    val time: List<String>

)