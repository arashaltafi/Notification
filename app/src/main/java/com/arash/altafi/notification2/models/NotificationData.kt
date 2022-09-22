package com.arash.altafi.notification2.models

data class NotificationData(
    val title: String,
    val message: String,
    val image: String? = null
)