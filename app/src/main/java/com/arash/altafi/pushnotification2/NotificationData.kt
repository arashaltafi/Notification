package com.arash.altafi.pushnotification2

data class NotificationData(
    val title: String,
    val message: String,
    val image: String? = null
)