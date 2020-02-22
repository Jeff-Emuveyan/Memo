package com.state.memo.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.state.memo.util.Repository

class MainActivityViewModel : ViewModel(){

    fun listenForUserSignOut(context: Context) = Repository(context).listenForUserSignOut {
            if (it == null){//user has Signed Out
                //delete user from db:
                Repository(context).deleteUser(viewModelScope, 1)
            }

    }
}