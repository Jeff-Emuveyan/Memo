package com.state.memo.ui.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.util.POST
import com.state.memo.util.PostStatus
import com.state.memo.util.Repository

class CreatePostViewModel : ViewModel() {

    suspend fun post(context: Context, data: Data): Task<DocumentReference>{
        val user = Repository(context!!).getUserSynchronously(1)
        val post = Post(user, data, System.currentTimeMillis())
        return Repository(context!!).postData(post)
    }


    fun collectAndValidateData(userData: Data): Boolean{
        return !((userData.text == null || userData.text == "") &&
                userData.imagePath == null && userData.videPath == null)
    }

}
