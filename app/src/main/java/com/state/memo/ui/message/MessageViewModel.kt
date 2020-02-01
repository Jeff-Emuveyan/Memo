package com.state.memo.ui.message

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.state.memo.database.Repository
import com.state.memo.model.User

class MessageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Message Fragment"
    }
    val text: LiveData<String> = _text

    fun getUser(context: Context): LiveData<User> = Repository(context).user
}