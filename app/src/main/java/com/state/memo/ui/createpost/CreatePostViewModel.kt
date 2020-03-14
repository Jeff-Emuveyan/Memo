package com.state.memo.ui.createpost

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.data.createPost.CreatePostRepository

class CreatePostViewModel : ViewModel() {

    val postStatus = MutableLiveData<Boolean>().apply {
        value = null
    }

    suspend fun post(context: Context, data: Data){
        val user = CreatePostRepository(context!!).getUserSynchronously(1)
        val post = Post(user, data, System.currentTimeMillis())
        val task = CreatePostRepository(context!!).postData(post)
        task.addOnCompleteListener {
            postStatus.value = it.isSuccessful
        }
    }


    infix fun collectAndValidateData(userData: Data): Boolean{
        return !((userData.text == null || userData.text == "") &&
                userData.imagePath == null && userData.videPath == null)
    }

}
