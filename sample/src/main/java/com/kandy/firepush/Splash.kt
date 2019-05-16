package com.kandy.firepush

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Karandeep Atwal on 2019-05-16.
 */
class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler().postDelayed({
            if (!isFinishing){
                startActivity(Intent(this@Splash, MainActivity::class.java))
                finish()
            }
        }, 3500)
    }
}