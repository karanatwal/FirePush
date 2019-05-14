package com.kandy.firepush

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firepush.Fire

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Fire.create()
            .setTitle("")
            .setColor("")
            .toIds("","")
            .push()


        Fire.createDataPayloadOnly()
            .add("","")
            .add("","")
            .toTopic("")
            .push()

    }
}
