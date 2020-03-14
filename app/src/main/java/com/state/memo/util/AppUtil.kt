package com.state.memo.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.state.memo.R
import java.util.*

fun showSnackMessage(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
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