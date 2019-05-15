package com.kandy.firepush

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import com.kandy.firepush.R
import androidx.appcompat.app.AppCompatActivity
import com.firepush.Fire
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fire.init("AAAAmN2v_Ig:APA91bH3Ckb4WHFqfS21RASHppSfu57SxkiN-bH4uFFvULrXv-9t9rmT4ncgEgG9FSnrhofYsXFjxN7d57HASAOmz4kNTp8tgDDW4XpzZNK16L3r4vRSzrVZsaVc5pXdzqHDb-wlBa2X")

        btn.setOnClickListener {
            Fire.create()
                .setTitle("My FirePush Title")
                .setBody("My FirePush Title Body")
                .addData("title","Amrinder demo")
                .setCallback {
                    text_tv.text = it
                }
                .toIds("eyCzqGfG4LE:APA91bEkm-fl1VtIONCz5sSb2vniNox1qgDA2pRis8vnd0YLtPViXQbAV2QzxWZfchARl4BX6_IZ40LTLuxI0WqoECH2d_LzRX9zaMnm-3wXHMuhFtp8HH0Mugj9lR98dTUfOSbiRyME")
                .push()
        }





//        Fire.createDataPayloadOnly()
//            .add("","")
//            .add("","")
//            .toTopic("")
//            .push()

    }
}
