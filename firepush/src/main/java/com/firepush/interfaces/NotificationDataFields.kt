package com.firepush.interfaces

import com.firepush.model.FirePushPriority
import com.firepush.model.PushCallback

interface NotificationDataFields {
    fun setTitle(title: String): NotificationDataFields
    fun setBody(body: String): NotificationDataFields
    fun setSound(sound: String): NotificationDataFields
    fun setBadgeCount(badgeCount: Int): NotificationDataFields
    fun setClickAction(click_action: String): NotificationDataFields
    fun setIcon(icon: String): NotificationDataFields
    fun setAndroidChannelId(android_channel_id: String): NotificationDataFields
    fun setTag(tag: String): NotificationDataFields
    fun setColor(color: String): NotificationDataFields

    fun addData(key: String, value: String): NotificationDataFields
    fun addData(hashMap: HashMap<String, String>): NotificationDataFields

    fun toTopic(topic: String): Target
    fun toIds(vararg ids: String): Target
    fun toCondition(condition: String): Target

    fun setPriority(firePushPriority: FirePushPriority): NotificationDataFields

    fun setCallback(callback: (PushCallback, Exception?) -> Unit): NotificationDataFields
}