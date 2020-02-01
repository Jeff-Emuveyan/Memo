package com.state.memo.ui.setttings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.state.memo.database.Repository
import com.state.memo.model.User

class SettingsViewModel : ViewModel() {

    fun getUser(context: Context): LiveData<User> = Repository(context).user
}