package com.firepush

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Karandeep Atwal on 16/04/19.
 */
object Fire : Push {

    fun create(): NotificationFields = PayloadBuilder()

    fun createDataPayloadOnly(): DataFields = DataPayloadBuilder()

    private fun sendToTopic(notificationPayload: NotificationPayload,priority: FirePushPriority){

    }

    private class PayloadBuilder : NotificationFields, Target {

        private var title: String? = null
        private var body: String? = null
        private var sound: String? = null
        private var badge: String? = null
        private var clickAction: String? = null
        private var icon: String? = null
        private var androidChannelId: String? = null
        private var tag: String? = null
        private var color: String? = null
        private var firePushPriority: FirePushPriority = FirePushPriority.NORMAL
        private var callback: ((String)-> Unit)? = null

        private var topic: String? = null
        private var condition: String? = null
        private var ids: JSONArray? = null


        override fun setTitle(title: String): NotificationFields = apply { this.title = title }
        override fun setBody(body: String): NotificationFields = apply { this.body = body }
        override fun setSound(sound: String): NotificationFields = apply { this.sound = sound }
        override fun setBadgeCount(badgeCount: Int): NotificationFields = apply { this.badge = badgeCount.toString() }
        override fun setClickAction(click_action: String): NotificationFields =
            apply { this.clickAction = click_action }

        override fun setIcon(icon: String): NotificationFields = apply { this.icon = icon }
        override fun setAndroidChannelId(android_channel_id: String): NotificationFields =
            apply { this.androidChannelId = android_channel_id }

        override fun setTag(tag: String): NotificationFields = apply { this.tag = tag }
        override fun setColor(color: String): NotificationFields = apply { this.color = color }
        override fun setPriority(firePushPriority: FirePushPriority) =
            apply { this.firePushPriority = firePushPriority }
        override fun setCallback(callback: (String)-> Unit) = apply { this.callback = callback }

        override fun toTopic(topic: String) = apply { this.topic = topic }

        override fun toIds(vararg ids: String) = apply { this.ids = JSONArray(ids) }

        override fun toCondition(condition: String) = apply { this.condition = condition }


        override fun push() = Fire.apply {
            val notificationPayload = NotificationPayload(
                title ?: error("Title must be there"),
                body ?: error("Body must be there"),
                sound,
                badge,
                clickAction,
                icon,
                androidChannelId,
                tag,
                color
            )

            when {
                topic != null -> 0
                ids != null -> 0
                condition != null -> 0
                else -> error("Either FCM target is null or not specified")
            }
        }
    }


    private class DataPayloadBuilder : DataFields, Target {

        private val jsonObject by lazy { JSONObject() }

        private var firePushPriority: FirePushPriority = FirePushPriority.NORMAL
        private var callback: ((String)-> Unit)? = null

        private var topic: String? = null
        private var condition: String? = null
        private var ids: JSONArray? = null

        override fun setPriority(firePushPriority: FirePushPriority) =
            apply { this.firePushPriority = firePushPriority }
        override fun setCallback(callback: (String)-> Unit) = apply { this.callback = callback }

        override fun add(key: String, value: String) = apply { jsonObject.put(key, value) }

        override fun add(hashMap: HashMap<String, String>) = apply {
            hashMap.forEach {
                jsonObject.put(it.key, it.value)
            }
        }

        override fun toTopic(topic: String) = apply { this.topic = topic }

        override fun toIds(vararg ids: String) = apply { this.ids = JSONArray(ids) }

        override fun toCondition(condition: String) = apply { this.condition = condition }

        override fun push() = Fire.apply {

        }

    }

}

