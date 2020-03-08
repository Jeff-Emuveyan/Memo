package com.state.memo.model

import androidx.annotation.Keep

@Keep
class Post(){

    lateinit var user: User
    lateinit var data: Data
    var time: Long? = null

    constructor(user: User, data: Data, time: Long): this(){
        this.user = user
        this.data = data
        this.time = time
    }
}