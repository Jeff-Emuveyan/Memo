package com.state.memo.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.state.memo.database.Repository

class MainActivityViewModel : ViewModel(){

    /** Choose authentication providers **/
    fun getAuthProviders() = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

    var userHasLoggedIn: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun signOut(context: Context){
       Repository(context).signOut()
    }
}