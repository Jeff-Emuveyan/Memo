package com.state.memo.ui.createpost

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.data.createPost.CreatePostRepository

class CreatePostViewModel : ViewModel() {

    suspend fun post(context: Context, data: Data): Task<DocumentReference>{
        val user = CreatePostRepository(context!!).getUserSynchronously(1)
        val post = Post(user, data, System.currentTimeMillis())
        return CreatePostRepository(context!!).postData(post)
    }


    fun collectAndValidateData(userData: Data): Boolean{
        return !((userData.text == null || userData.text == "") &&
                userData.imagePath == null && userData.videPath == null)
    }

}
