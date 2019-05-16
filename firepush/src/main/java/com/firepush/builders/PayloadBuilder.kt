package com.firepush.builders

import com.firepush.*
import com.firepush.interfaces.NotificationDataFields
import com.firepush.interfaces.Target
import com.firepush.model.FirePushPriority
import com.firepush.model.PushCallback
import org.json.JSONArray
import org.json.JSONObject

internal class PayloadBuilder : NotificationDataFields, Target {

    private val notificationObject by lazy { JSONObject() }

    private var title: String? = null
    private var body: String? = null
    private var sound: String? = null
    private var badge: String? = null
    private var clickAction: String? = null
    private var icon: String? = null
    private var androidChannelId: String? = null
    private var tag: String? = null
    private var color: String? = null

    private val dataObject by lazy { JSONObject() }

    private var topic: String? = null
    private var condition: String? = null
    private var ids: JSONArray? = null

    private var firePushPriority: FirePushPriority = FirePushPriority.NORMAL
    private var callback: ((PushCallback, Exception?) -> Unit)? = null


    override fun setTitle(title: String): NotificationDataFields = apply { this.title = title }
    override fun setBody(body: String): NotificationDataFields = apply { this.body = body }
    override fun setSound(sound: String): NotificationDataFields = apply { this.sound = sound }
    override fun setBadgeCount(badgeCount: Int): NotificationDataFields =
        apply { this.badge = badgeCount.toString() }

    override fun setClickAction(click_action: String): NotificationDataFields =
        apply { this.clickAction = click_action }

    override fun setIcon(icon: String): NotificationDataFields = apply { this.icon = icon }
    override fun setAndroidChannelId(android_channel_id: String): NotificationDataFields =
        apply { this.androidChannelId = android_channel_id }

    override fun setTag(tag: String): NotificationDataFields = apply { this.tag = tag }
    override fun setColor(color: String): NotificationDataFields = apply { this.color = color }

    override fun addData(key: String, value: String) = apply { dataObject.put(key, value) }

    override fun addData(hashMap: HashMap<String, String>) = apply {
        hashMap.forEach {
            dataObject.put(it.key, it.value)
        }
    }

    override fun toTopic(topic: String) = apply { this.topic = topic }

    override fun toIds(vararg ids: String) = apply { this.ids = JSONArray(ids) }

    override fun toCondition(condition: String) = apply { this.condition = condition }


    override fun setPriority(firePushPriority: FirePushPriority) =
        apply { this.firePushPriority = firePushPriority }

    override fun setCallback(callback: (PushCallback, Exception?) -> Unit) = apply { this.callback = callback }


    override fun push() = Fire.apply {

        AUTH_KEY ?: error("Auth key not specified. Please use init function first.")
        title ?: error("Title must be there")
        body ?: error("Body must be there")

        notificationObject.apply {
            put("title", title)
            put("body", body)

            if (sound != null)
                put("sound", sound)
            if (badge != null)
                put("badge", badge)
            if (clickAction != null)
                put("click_action", clickAction)
            if (icon != null)
                put("icon", icon)
            if (androidChannelId != null)
                put("android_channel_id", androidChannelId)
            if (tag != null)
                put("tag", tag)
            if (color != null)
                put("color", color)
        }

        sendTo(
            topic = topic,
            ids = ids,
            condition = condition,
            notificationObject = notificationObject,
            dataObject = if (dataObject.length() > 0) dataObject else null,
            priority = firePushPriority,
            callback = callback
        )
    }
}
