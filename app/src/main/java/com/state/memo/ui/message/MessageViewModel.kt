package com.state.memo.ui.message

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.state.memo.database.Repository
import com.state.memo.model.User

class MessageViewModel : ViewModel() {

    fun getUser(context: Context): LiveData<User> = Repository(context).user
}