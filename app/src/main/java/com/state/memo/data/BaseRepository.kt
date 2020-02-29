package com.state.memo.data

import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.state.memo.data.database.room.AppDatabase
import com.state.memo.model.Post
import com.state.memo.model.User
import com.state.memo.data.network.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseRepository(var context: Context) {

    val db = AppDatabase.getDatabase(context)

    /***fetch a user asynchronously ie returns a Livedata so there is no need to make it suspend.
     * It is already asynchronous**/
    fun getUser(id: Int) = db.userDao().getUser(id)


    /***fetch a user synchronously ie does not return Livedata. Room won't let us read data synchronously,
     *So we must either return a LiveData which will do the async work or make this function suspend**/
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

    /**
     * A listener to notify us if the user has signed out of Firebase*
     * ***/
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


    /**Determines if the current user is an Admin
     * **/
    suspend fun isUserAdmin(): Boolean{
        //get the current user:
        val currentUser = getUserSynchronously(1)

        //get the list of admin emails:
        val admin = db.adminDao().getAdminSynchronously(1)

        if(admin?.users != null && admin.users.isNotEmpty() && currentUser?.email != null) {
            for (email in admin.users){
                return (currentUser.email == email)
            }
        }
        return false
    }

}