package com.state.memo.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.state.memo.model.Post
import com.state.memo.util.ADMIN
import com.state.memo.util.POST


object NetworkHelper {


    fun upload(post: Post): Task<DocumentReference>{
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        return db.collection(POST).add(post)
    }

    fun fetchAdmin(flavour: String): Task<DocumentSnapshot>{
        val db = FirebaseFirestore.getInstance()
        return db.collection(ADMIN).document(flavour).get()
    }

}