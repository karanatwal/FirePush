package com.firepush.model

import org.json.JSONObject

/**
 * Created by https://karanatwal.github.io on 2019-05-16.
 */
data class PushCallback(
    val isSent: Boolean,
    val jsonObject: JSONObject?
)