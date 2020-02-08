package com.state.memo.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.state.memo.database.room.AppDatabase
import com.state.memo.model.User

class Repository(var context: Context) {

    private val db = AppDatabase.getDatabase(context)

    val user: LiveData<User> = db.userDao().getUser()

    fun listenForUserSignOut(userState: (FirebaseUser?)->Unit) = FirebaseAuth.getInstance().addAuthStateListener {
        userState.invoke(it.currentUser)
    }


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