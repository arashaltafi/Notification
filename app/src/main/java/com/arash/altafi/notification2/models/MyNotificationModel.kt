package com.arash.altafi.notification2.models

import com.google.gson.annotations.SerializedName

data class MyNotificationModel(

    @field:SerializedName("body")
    val body: List<String>

    )