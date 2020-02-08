package com.state.memo.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.state.memo.util.Repository

class MainActivityViewModel : ViewModel(){

    /** Choose authentication providers **/
    fun getAuthProviders() = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

    var user: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>().apply {
        value = null
    }

    fun signOut(context: Context){
       Repository(context).signOut()
    }


    fun listenForUserSignOut(context: Context){
        Repository(context).listenForUserSignOut {
            user.value = it
        }
    }
}