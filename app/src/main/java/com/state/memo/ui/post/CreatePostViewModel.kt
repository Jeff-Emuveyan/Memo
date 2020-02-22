package com.state.memo.ui.post

import android.content.Context
import androidx.lifecycle.ViewModel
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.util.PostStatus
import com.state.memo.util.Repository

class CreatePostViewModel : ViewModel() {

    suspend fun post(context: Context, data: Data, status: (PostStatus)-> Unit){

        if(collectAndValidateData(data)){
            val user = Repository(context!!).getUserSynchronously(1)
            val post = Post(user, data, System.currentTimeMillis())
        }else{
           status.invoke(PostStatus.INVALID_INPUT)
        }

    }


    fun collectAndValidateData(userData: Data): Boolean{
        return !((userData.text == null || userData.text == "") &&
                userData.imagePath == null && userData.videPath == null)
    }

}
