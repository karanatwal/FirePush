package com.firepush

/**
 * Created by Karandeep Atwal on 16/04/19.
 */
object FirePush{
    private lateinit var SERVER_KEY : String

    fun init(server_key : String){
        SERVER_KEY = server_key
    }

    fun create() = C()
}