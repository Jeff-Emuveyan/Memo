package com.state.memo.data.network

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.state.memo.model.Post
import com.state.memo.ui.createpost.CreatePostBottomSheet
import com.state.memo.ui.createpost.CreatePostFragment
import com.state.memo.util.ADMIN
import com.state.memo.util.Converters
import com.state.memo.util.PICTURES
import com.state.memo.util.POST


object NetworkHelper {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun upload(post: Post): Task<DocumentReference>{
        return db.collection(POST).add(post)
    }

    fun fetchAdmin(flavour: String): Task<DocumentSnapshot>{
        return db.collection(ADMIN).document(flavour).get()
    }

    fun getPosts(): Task<QuerySnapshot> {
        return db.collection(POST).get()
    }

    fun uploadMediaFile(uri: Uri): UploadTask{
        val firebaseStorage = FirebaseStorage.getInstance().reference
        val storageReference = firebaseStorage.child(PICTURES)
        return storageReference.putFile(uri)
    }

}