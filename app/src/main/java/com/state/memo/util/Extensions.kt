package com.state.memo.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String){
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}