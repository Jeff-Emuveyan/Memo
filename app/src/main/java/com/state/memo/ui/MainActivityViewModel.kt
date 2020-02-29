package com.state.memo.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.state.memo.data.BaseRepository
import com.state.memo.data.home.HomeRepository

class MainActivityViewModel : ViewModel(){

    fun listenForUserSignOut(context: Context) = HomeRepository(context).listenForUserSignOut {
            if (it == null){//user has Signed Out
                //delete user from db:
                HomeRepository(context).deleteUser(viewModelScope, 1)
            }

    }
}