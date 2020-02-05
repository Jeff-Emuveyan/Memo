package com.state.memo.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackMessage(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}