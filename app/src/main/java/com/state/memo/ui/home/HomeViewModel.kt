package com.state.memo.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.state.memo.data.home.HomeRepository
import com.state.memo.model.Post


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    var data: MutableLiveData<ArrayList<Post>> = MutableLiveData<ArrayList<Post>>().apply {
        value = null
    }

    /** Choose authentication providers **/
    fun getAuthProviders() = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())


    fun signOut(context: Context){
        HomeRepository(context).signOut()
    }


    suspend fun isUserAdmin(context: Context): Boolean{
        return HomeRepository(context).isUserAdmin()
    }


    fun getPosts(context: Context, onFailed: () -> Unit){
        val listOfPost = ArrayList<Post>()
        HomeRepository(context).getPosts().addOnCompleteListener {
            if(it.isSuccessful && it.result != null){
                for (document in it.result!!) {
                    val post = document.toObject(Post::class.java)
                    listOfPost.add(post)
                }
                data.value = listOfPost
            }else{
                onFailed.invoke()
            }
        }
    }
}