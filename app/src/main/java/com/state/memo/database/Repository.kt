package com.state.memo.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.state.memo.database.room.AppDatabase
import com.state.memo.model.User

class Repository(context: Context) {

    private val db = AppDatabase.getDatabase(context)

    val user: LiveData<User> = db.userDao().getUser()
}