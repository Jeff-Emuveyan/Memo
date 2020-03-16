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
        val user = CreatePostRepository(context!!).getUserSynchronously(1)
        val post = Post(user, data, System.currentTimeMillis())
        val task = CreatePostRepository(context!!).postData(post)
        task.addOnCompleteListener {
            postStatus.value = it.isSuccessful
        }
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

}
