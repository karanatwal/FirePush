package com.firepush

import com.firepush.Constants.AUTHORIZATION
import com.firepush.Constants.CONTENT_TYPE
import com.firepush.Constants.FCM_URL
import com.firepush.Constants.JSON
import com.firepush.Constants.KEY_PREFIX
import com.firepush.Constants.POST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by Karandeep Atwal on 16/04/19.
 */
object Fire : Push {

    private var AUTH_KEY: String? = null

    fun init(authKey: String) {
        AUTH_KEY = authKey
    }

    fun with(authKey: String) = apply { AUTH_KEY = authKey }

    fun create(): NotificationDataFields = PayloadBuilder()

    fun createDataPayloadOnly(): DataFields = DataPayloadBuilder()

    private fun sendTo(
        topic: String? = null,
        ids: JSONArray? = null,
        condition: String? = null,
        notificationObject: JSONObject? = null,
        dataObject: JSONObject? = null,
        priority: FirePushPriority,
        callback: ((String) -> Unit)? = null
    ) = CoroutineScope(Dispatchers.IO).launch {

        val payloadObject = JSONObject()

        payloadObject.put("priority", if (priority == FirePushPriority.NORMAL) "normal" else "high")

        if (notificationObject != null)
            payloadObject.put("notification", notificationObject)
        if (dataObject != null)
            payloadObject.put("data", dataObject)
        if (topic != null)
            payloadObject.put("to", topic)
        if (ids != null)
            payloadObject.put("registration_ids", ids)
        if (condition != null)
            payloadObject.put("condition", condition)

        val url = URL(FCM_URL)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = POST
        connection.setRequestProperty(AUTHORIZATION, KEY_PREFIX.plus(AUTH_KEY))
        connection.setRequestProperty(CONTENT_TYPE, JSON)
        connection.doOutput = true
        try {
            val outputStream = connection.outputStream
            outputStream.write(payloadObject.toString().toByteArray())

            val inputStream = connection.inputStream

            val scanner = Scanner(inputStream).useDelimiter("\\A")
            val response = if (scanner.hasNext()) scanner.next().replace(",", ",\n") else ""

            withContext(Dispatchers.Main) {
                callback?.invoke(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                callback?.invoke(e.message ?: "Exception occurs")
            }
        }
    }


    private class PayloadBuilder : NotificationDataFields, Target {

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
        private var callback: ((String) -> Unit)? = null


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

        override fun setCallback(callback: (String) -> Unit) = apply { this.callback = callback }


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


    private class DataPayloadBuilder : DataFields, Target {

        private val dataObject by lazy { JSONObject() }

        private var firePushPriority: FirePushPriority = FirePushPriority.NORMAL
        private var callback: ((String) -> Unit)? = null

        private var topic: String? = null
        private var condition: String? = null
        private var ids: JSONArray? = null

        override fun setPriority(firePushPriority: FirePushPriority) =
            apply { this.firePushPriority = firePushPriority }

        override fun setCallback(callback: (String) -> Unit) = apply { this.callback = callback }

        override fun add(key: String, value: String) = apply { dataObject.put(key, value) }

        override fun add(hashMap: HashMap<String, String>) = apply {
            hashMap.forEach {
                dataObject.put(it.key, it.value)
            }
        }

        override fun toTopic(topic: String) = apply { this.topic = topic }

        override fun toIds(vararg ids: String) = apply { this.ids = JSONArray(ids) }

        override fun toCondition(condition: String) = apply { this.condition = condition }

        override fun push() = Fire.apply {
            AUTH_KEY ?: error("Auth key not specified. Please use init function first.")

            sendTo(
                topic = topic,
                ids = ids,
                condition = condition,
                dataObject = dataObject,
                priority = firePushPriority,
                callback = callback
            )
        }
    }

}

