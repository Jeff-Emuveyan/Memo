package com.state.memo.data.createPost

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.state.memo.data.BaseRepository
import com.state.memo.data.network.NetworkHelper
import com.state.memo.model.Post
import java.io.File


class CreatePostRepository(var cxt: Context): BaseRepository(cxt) {

    /**
     *
     *Used to post just textual data
     * **/
    fun postData(post: Post): Task<DocumentReference> = NetworkHelper.upload(post)


    /**
     * Used to post just media data ie Image or video
     * ***/
    fun postDataContainingMedia(post: Post, onFinish: (successful: Boolean)-> Unit){
        //we upload the media file first. But which of the media file?
        val imagePath = post.data.imagePath
        val videoPath = post.data.videPath
        var mediaFilePath = if((imagePath != null && imagePath.isNotEmpty())){
             imagePath
        }else if (videoPath != null && videoPath.isNotEmpty()){
            videoPath
        }else{ "" }
        val uri = Uri.fromFile(File(mediaFilePath))
        NetworkHelper.uploadMediaFile(uri)
            .addOnProgressListener {

            }
            .addOnSuccessListener {
                onFinish.invoke(true)
            }
            .addOnFailureListener{
                onFinish.invoke(false)
                Toast.makeText(cxt, it.message, Toast.LENGTH_LONG).show()
            }
    }
}