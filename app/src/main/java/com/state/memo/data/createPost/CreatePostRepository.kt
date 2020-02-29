package com.state.memo.data.createPost

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.state.memo.data.BaseRepository
import com.state.memo.data.network.NetworkHelper
import com.state.memo.model.Post

class CreatePostRepository(var cxt: Context): BaseRepository(cxt) {

    fun postData(post: Post): Task<DocumentReference> = NetworkHelper.upload(post)
}