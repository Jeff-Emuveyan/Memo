package com.state.memo.ui.createpost

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaiselrahman.filepicker.model.MediaFile
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.data.createPost.CreatePostRepository
import com.state.memo.model.MediaFileUploadStatus

class CreatePostViewModel : ViewModel() {


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
