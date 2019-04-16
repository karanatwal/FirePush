package com.kandy.firepush

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firepush.FirePush

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)


        FirePush.init("")

        FirePush.create()
            .setTitle("")
            .setMessage("")
            .setTitle("")

    }
}
