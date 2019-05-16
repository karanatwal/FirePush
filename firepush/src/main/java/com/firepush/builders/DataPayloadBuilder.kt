package com.firepush.builders

import com.firepush.*
import com.firepush.interfaces.DataFields
import com.firepush.interfaces.Target
import com.firepush.model.FirePushPriority
import com.firepush.model.PushCallback
import org.json.JSONArray
import org.json.JSONObject

internal class DataPayloadBuilder : DataFields, Target {

    private val dataObject by lazy { JSONObject() }

    private var firePushPriority: FirePushPriority = FirePushPriority.NORMAL
    private var callback: ((PushCallback, Exception?) -> Unit)? = null

    private var topic: String? = null
    private var condition: String? = null
    private var ids: JSONArray? = null

    override fun setPriority(firePushPriority: FirePushPriority) =
        apply { this.firePushPriority = firePushPriority }

    override fun setCallback(callback: (PushCallback, Exception?) -> Unit) = apply { this.callback = callback }

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
