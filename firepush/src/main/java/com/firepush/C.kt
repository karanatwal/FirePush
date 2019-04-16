package com.firepush

class C {
    private var title: String = ""
    private var message: String = ""

    fun setTitle(title: String): C {
        this@C.title = title
        return this@C
    }

    fun setMessage(message: String): C {
        this@C.message = message
        return this@C
    }
}