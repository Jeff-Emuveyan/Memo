package com.state.memo.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.state.memo.data.BaseRepository
import com.state.memo.data.createPost.CreatePostRepository
import com.state.memo.data.home.HomeRepository
import com.state.memo.model.Data
import com.state.memo.model.MediaFileUploadStatus
import com.state.memo.model.Post
import com.state.memo.util.userSelectedMediaFile


/**
 * This class acts as a SharedViewModel for all fragments of this application.
 * It basically handles processes that have to be global because more than one Fragment is
 * interested or in it.
 ***/
class MainActivityViewModel : ViewModel(){

    private lateinit var createPostRepository: CreatePostRepository


    /**
     * Used to know when a post has been sent successfully or not
     **/
    val postStatus = MutableLiveData<Boolean>().apply {
        value = null
    }

    /**
     * Used to know whether a media file is being uploaded or not
     ***/
    val mediaFileUploadStatus = MutableLiveData<MediaFileUploadStatus>().apply {
        value = MediaFileUploadStatus.DEFAULT
    }


    /**
     * Used to know whether the user has signed out
     **/
    fun listenForUserSignOut(context: Context) = HomeRepository(context).listenForUserSignOut {
            if (it == null){//user has Signed Out
                //delete user from db:
                HomeRepository(context).deleteUser(viewModelScope, 1)
            }
    }


    /**
     * Used to post data to Firestore
     ***/
    suspend fun post(context: Context, data: Data){

        fun sync(post: Post){
            CreatePostRepository(context).postData(post).addOnCompleteListener {
                postStatus.postValue(it.isSuccessful)
            }
        }

        val user = CreatePostRepository(context).getUserSynchronously(1)
        val post = Post(user, data, System.currentTimeMillis())

        //check if the data contains media file:
        if(userSelectedMediaFile(post)){ //the user selected an image or video file

            CreatePostRepository(context).postDataContainingMedia(post,

                mediaFileUploadInProgress = { totalBytes, bytesTransferred ->
                    val status = MediaFileUploadStatus.UPLOADING
                    status.totalBytes = totalBytes
                    status.bytesTransferred = bytesTransferred
                    mediaFileUploadStatus.postValue(status)
            }
                ,onFinish = {
                if(it){//the media file was uploaded successfully so proceed to upload the rest data
                    mediaFileUploadStatus.postValue(MediaFileUploadStatus.UPLOAD_COMPLETED)
                    sync(post)
                }else{
                    postStatus.postValue(false)
                }
            })
        }else{//user didn't attach a media file:
            sync(post)
        }
    }


    fun cancelFileUpload(){
        CreatePostRepository.cancelFileUpload()
        mediaFileUploadStatus.postValue(MediaFileUploadStatus.CANCELLED)
    }
}