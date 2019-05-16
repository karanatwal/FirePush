package com.firepush

import com.firepush.utils.Constants.AUTHORIZATION
import com.firepush.utils.Constants.CONDITION
import com.firepush.utils.Constants.CONTENT_TYPE
import com.firepush.utils.Constants.FCM_URL
import com.firepush.utils.Constants.ID
import com.firepush.utils.Constants.JSON
import com.firepush.utils.Constants.KEY_PREFIX
import com.firepush.utils.Constants.POST
import com.firepush.utils.Constants.TOPIC
import com.firepush.builders.DataPayloadBuilder
import com.firepush.builders.PayloadBuilder
import com.firepush.interfaces.DataFields
import com.firepush.interfaces.NotificationDataFields
import com.firepush.interfaces.Push
import com.firepush.model.FirePushPriority
import com.firepush.model.PushCallback
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
 * Created by https://karanatwal.github.io on 16/04/19.
 */
object Fire : Push {

    internal var AUTH_KEY: String? = null

    fun init(authKey: String) {
        AUTH_KEY = authKey
    }

    fun with(authKey: String) = apply { AUTH_KEY = authKey }

    fun create(): NotificationDataFields = PayloadBuilder()

    fun createDataPayloadOnly(): DataFields = DataPayloadBuilder()

    internal fun sendTo(
        topic: String? = null,
        ids: JSONArray? = null,
        condition: String? = null,
        notificationObject: JSONObject? = null,
        dataObject: JSONObject? = null,
        priority: FirePushPriority,
        callback: ((PushCallback, Exception?) -> Unit)? = null
    ) = CoroutineScope(Dispatchers.IO).launch {

        val payloadObject = JSONObject()

        payloadObject.put("priority", if (priority == FirePushPriority.NORMAL) "normal" else "high")

        if (notificationObject != null)
            payloadObject.put("notification", notificationObject)
        if (dataObject != null)
            payloadObject.put("data", dataObject)
        if (topic != null)
            payloadObject.put(TOPIC, topic)
        if (ids != null)
            payloadObject.put(ID, ids)
        if (condition != null)
            payloadObject.put(CONDITION, condition)

        val url = URL(FCM_URL)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = POST
        connection.setRequestProperty(AUTHORIZATION, KEY_PREFIX.plus(AUTH_KEY))
        connection.setRequestProperty(CONTENT_TYPE, JSON)
        connection.doOutput = true
        try {
            val outputStream = connection.outputStream
            outputStream.write(payloadObject.toString().toByteArray())

            val responseOK = connection.responseCode == HttpURLConnection.HTTP_OK
            val inputStream = if (responseOK) connection.inputStream else connection.errorStream

            val scanner = Scanner(inputStream).useDelimiter("\\A")
            val response = if (scanner.hasNext()) scanner.next().replace(",", ",\n") else ""

            if (responseOK) {
                val jsonObject = JSONObject(response)
                val isSuccess = if (jsonObject.has("success")) jsonObject.getInt("success") == 1 else false
                withContext(Dispatchers.Main) {
                    callback?.invoke(PushCallback(isSuccess, JSONObject(response)), null)
                }
            } else throw Exception("Response Code: ".plus(connection.responseCode).plus(" Error: ".plus(response)))

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                callback?.invoke(PushCallback(false, null), e)
            }
        }
    }

}

