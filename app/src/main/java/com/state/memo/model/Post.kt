package com.state.memo.model

import androidx.annotation.Keep

@Keep
data class Post(val user: User, val data: Data, val time: Long)