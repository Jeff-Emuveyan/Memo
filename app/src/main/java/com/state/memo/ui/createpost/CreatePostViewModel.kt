package com.state.memo.ui.createpost

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaiselrahman.filepicker.model.MediaFile
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.data.createPost.CreatePostRepository

class CreatePostViewModel : ViewModel() {

    val postStatus = MutableLiveData<Boolean>().apply {
        value = null
    }

    suspend fun post(context: Context, data: Data){
        val user = CreatePostRepository(context).getUserSynchronously(1)
        //check if the data contains
        val post = Post(user, data, System.currentTimeMillis())
        if(userSelectedMediaFile(post)){ //the user selected an image or video
            CreatePostRepository(context).postDataContainingMedia(post, onFinish = {
                postStatus.value = it
            })
        }else{//user didn't attach a media file:
            val task = CreatePostRepository(context).postData(post)
            task.addOnCompleteListener {
                postStatus.value = it.isSuccessful
            }
        }
    }



    private fun userSelectedMediaFile(post: Post): Boolean {
        val imagePath = post.data.imagePath
        val videoPath = post.data.videPath
        if((imagePath != null && imagePath.isNotEmpty()) || (videoPath != null && videoPath.isNotEmpty())){
            return true
        }
        return false
    }

    infix fun collectAndValidateData(userData: Data): Boolean{
        return !((userData.text == null || userData.text == "") &&
                userData.imagePath == null && userData.videPath == null)
    }


    fun getBitmapFromResult(context: Context, imageFiles: ArrayList<MediaFile>?): Bitmap? {
        //users can only select one image file
        if(imageFiles != null && imageFiles.size > 0){
            val firstMediaFile = imageFiles[0]
            val uri = firstMediaFile.uri
            return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        return null
    }


    fun getFilePathFromResult(context: Context, imageFiles: ArrayList<MediaFile>?): String? {
        if(imageFiles != null && imageFiles.size > 0){
            val firstMediaFile = imageFiles[0]
            return  firstMediaFile.path
        }
        return null
    }

}
