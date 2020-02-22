package com.state.memo.network

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.state.memo.model.Post
import com.state.memo.util.POST
import com.state.memo.util.PostStatus


object NetworkHelper {


    fun upload(post: Post): PostStatus{
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val documentReference = db.collection(POST).add(post).result
        return try {
            if(documentReference != null){
                PostStatus.SUCCESSFUL
            }else{
                PostStatus.FAILED
            }
        } catch (e: Exception) {
            PostStatus.FAILED
        }
    }
}