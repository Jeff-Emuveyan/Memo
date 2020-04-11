package com.state.memo.util

import android.app.Activity
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.state.memo.R
import com.state.memo.model.Post
import java.util.*

/*** Global constants ****/
val mediaFileUploadStatus = "MediaFileUploadStatus"


fun showSnackMessage(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun showSnackMessage(activity: FragmentActivity, message: String){
    Snackbar.make(activity.window!!.decorView, message, Snackbar.LENGTH_LONG).show()
}


fun getTextBackgroundImage(): Int{
    //currently, we only support 11 background images
    val listOfBackgroundImages = arrayListOf<Int>(
        R.drawable.bg0, R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4,
        R.drawable.bg5, R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9,
        R.drawable.bg10
    )
    //generate a random number from 0-10 ie 11 possible outcomes:
    var randomInt = Random().nextInt(7+1) // [0...11] [min = 0, max = 11

    return listOfBackgroundImages[randomInt]
}


fun userSelectedMediaFile(post: Post): Boolean {
    val imagePath = post.data.imagePath
    val videoPath = post.data.videPath
    if((imagePath != null && imagePath.isNotEmpty()) || (videoPath != null && videoPath.isNotEmpty())){
        return true
    }
    return false
}


/***
 * This is used to search and return the media file path
 ***/
fun getMediaFilePath(post: Post): String{
    val imagePath = post.data.imagePath
    val videoPath = post.data.videPath

    return if((imagePath != null && imagePath.isNotEmpty())){
        imagePath
    }else if (videoPath != null && videoPath.isNotEmpty()){
        videoPath
    }else{ "" }
}

