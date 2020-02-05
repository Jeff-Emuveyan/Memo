package com.state.memo.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.firebase.ui.auth.AuthUI
import com.state.memo.database.room.AppDatabase
import com.state.memo.model.User

class Repository(var context: Context) {

    private val db = AppDatabase.getDatabase(context)

    val user: LiveData<User> = db.userDao().getUser()


    fun signOut(){
        AuthUI.getInstance().signOut(context).addOnCompleteListener {
            //this call will cause addAuthStateListener() in MainActivity to trigger.
            if (it.isSuccessful){
                Log.e("User state", "Signed Out")
            }
        }.addOnFailureListener {
            //this call will cause addAuthStateListener()  in MainActivity to trigger.
            Log.e("User state", "Signed Out failed")
        }
    }
}