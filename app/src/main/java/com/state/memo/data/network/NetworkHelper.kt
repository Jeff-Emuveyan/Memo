package com.state.memo.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.state.memo.model.Post
import com.state.memo.util.POST


object NetworkHelper {


    fun upload(post: Post): Task<DocumentReference>{
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        return db.collection(POST).add(post)
    }
}