package com.kandy.firepush

import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.firepush.Fire
import com.firepush.model.PushCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api_key.setText(getString(R.string.auth_key))
        target.setText(getString(R.string.demoTokenId))


        btn.setOnClickListener {
            //No need to init every time.
            // Just use this function in BaseActivity or in Application class onCreate
            Fire.init(api_key.text.toString())

            val selectedTarget = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text
            when {
                selectedTarget.contains("Token") -> sendToToken()
                selectedTarget.contains("Topic") -> sendToTopic()
                selectedTarget.contains("Condition") -> sendToCondition()
            }
        }
    }

    private fun sendToToken() {
        Fire.create()
            .setTitle(titleTv.text.toString())
            .setBody(body.text.toString())
            .setCallback { pushCallback, e ->
                if (e == null) {
                    response_tv.text = createResponseText(pushCallback)
                } else response_tv.text = e.message
            }
            .toIds(target.text.toString())
            .push()
    }

    private fun sendToTopic() {
        Fire.create()
            .setTitle(titleTv.text.toString())
            .setBody(body.text.toString())
            .setCallback { pushCallback, e ->
                if (e == null) {
                    response_tv.text = createResponseText(pushCallback)
                } else response_tv.text = e.message
            }
            .toTopic(target.text.toString())
            .push()
    }

    private fun sendToCondition() {
        Fire.create()
            .setTitle(titleTv.text.toString())
            .setBody(body.text.toString())
            .setCallback { pushCallback, e ->
                if (e == null) {
                    response_tv.text = createResponseText(pushCallback)
                } else response_tv.text = e.message
            }
            .toCondition(target.text.toString())
            .push()
    }

    private fun createResponseText(pushCallback: PushCallback): String {
        var text = "Response:\n\n"
        if (pushCallback.isSent) {
            text = text.plus("Message Sent Successfully").plus("\n\n").plus(pushCallback.jsonObject.toString())
        } else text = text.plus("Fails to send message").plus("\n\n").plus(pushCallback.jsonObject.toString())
        return text
    }
}
