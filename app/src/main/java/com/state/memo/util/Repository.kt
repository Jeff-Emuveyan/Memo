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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Repository(var context: Context) {

    private val db = AppDatabase.getDatabase(context)

    /***fetch a user asynchronously ie returns a Livedata**/
    fun getUser(id: Int) = db.userDao().getUser(id)

    /***fetch a user synchronously ie does not return Livedata**/
    suspend fun getUserSynchronously(id: Int) = db.userDao().getUserSynchronously(id)

    /***delete**/
    fun deleteUser(coroutineScope: CoroutineScope, id: Int){

        coroutineScope.launch {
            val user = getUserSynchronously(id)
            if (user != null){
                db.userDao().deleteUser(user)
            }
        }
    }

    fun listenForUserSignOut(userState: (FirebaseUser?)->Unit) =
        FirebaseAuth.getInstance().addAuthStateListener {
        userState.invoke(it.currentUser)
    }


    /**saves user locally**/
    fun saveUser(coroutineScope: CoroutineScope, id: Int, user: FirebaseUser?){
        user?.let {
            coroutineScope.launch {
                val newUser = User(id,
                    User.Type.PARENT, user.displayName ?: "You", user.email!!, user.photoUrl?.toString())

                val oldUser = getUserSynchronously(id)
                if (oldUser == null){
                    db.userDao().saveUser(newUser)
                }else{
                    db.userDao().updateUser(newUser)
                }

            }
        }
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