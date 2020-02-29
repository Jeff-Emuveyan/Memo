package com.state.memo.data.home

import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.state.memo.data.BaseRepository
import com.state.memo.data.network.NetworkHelper

class HomeRepository(var cxt: Context): BaseRepository(cxt) {

    fun signOut(){
        AuthUI.getInstance().signOut(context).addOnCompleteListener {
            //this call will cause listenForUserSignOut in MainActivity to trigger.
            if (it.isSuccessful){
                Log.e("User state", "Signed Out")
            }
        }.addOnFailureListener {
            //this call will cause listenForUserSignOut in MainActivity to trigger.
            Log.e("User state", "Signed Out failed")
        }
    }

    fun getPosts() = NetworkHelper.getPosts()
}