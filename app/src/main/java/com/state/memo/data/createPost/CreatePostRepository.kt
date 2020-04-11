package com.state.memo.data.createPost

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.UploadTask
import com.state.memo.data.BaseRepository
import com.state.memo.data.network.NetworkHelper
import com.state.memo.model.Post
import com.state.memo.util.getMediaFilePath
import java.io.File


class CreatePostRepository(var cxt: Context): BaseRepository(cxt) {

    companion object{
        private lateinit var mediaFileUploadTask: UploadTask

        fun cancelFileUpload(){
            mediaFileUploadTask?.cancel()
        }
    }


    /**
     *
     *Used to post just textual data
     * **/
    fun postData(post: Post): Task<DocumentReference> = NetworkHelper.upload(post)


    /**
     * Used to post just media data ie Image or video
     **/
    fun postDataContainingMedia(post: Post,
                                mediaFileUploadInProgress:(totalBytes: Int, bytesTransferred: Int) -> Unit,
                                onFinish: (successful: Boolean)-> Unit){

        //We upload the media file first. But which of the media file
        val uri = Uri.fromFile(File(getMediaFilePath(post)))
        mediaFileUploadTask = NetworkHelper.uploadMediaFile(uri)
        mediaFileUploadTask.addOnProgressListener {
                mediaFileUploadInProgress.invoke(it.totalByteCount.toInt(), it.bytesTransferred.toInt())
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