package com.firepush

data class NotificationPayload(
    val title: String,
    val body: String,
    val sound: String?,
    val badge: String?,
    val click_action: String?,
    val icon: String?,
    val android_channel_id: String?,
    val tag: String?,
    val color: String?
)