package com.firepush.interfaces

import com.firepush.model.FirePushPriority
import com.firepush.model.PushCallback

interface DataFields {
    fun setPriority(firePushPriority: FirePushPriority): DataFields
    fun setCallback(callback: (PushCallback, Exception?)-> Unit): DataFields

    fun add(key: String, value: String): DataFields
    fun add(hashMap: HashMap<String, String>): DataFields

    fun toTopic(topic: String): Target
    fun toIds(vararg ids: String): Target
    fun toCondition(condition: String): Target
}