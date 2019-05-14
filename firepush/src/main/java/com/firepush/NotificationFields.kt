package com.firepush

interface NotificationFields {
    fun setTitle(title: String): NotificationFields
    fun setBody(body: String): NotificationFields
    fun setSound(sound: String): NotificationFields
    fun setBadgeCount(badgeCount: Int): NotificationFields
    fun setClickAction(click_action: String): NotificationFields
    fun setIcon(icon: String): NotificationFields
    fun setAndroidChannelId(android_channel_id: String): NotificationFields
    fun setTag(tag: String): NotificationFields
    fun setColor(color: String): NotificationFields
    fun setPriority(firePushPriority: FirePushPriority): NotificationFields
    fun setCallback(callback: (String)-> Unit): NotificationFields

    fun toTopic(topic: String): Target
    fun toIds(vararg ids: String): Target
    fun toCondition(condition: String): Target
}