package com.state.memo.ui.home

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.state.memo.R
import kotlinx.android.synthetic.main.post_item.view.*

class PostItem(v: View): RecyclerView.ViewHolder(v) {

    lateinit var tvMessage: TextView
    lateinit var ivBanner: ImageView
    lateinit var context: Context

    constructor(context: Context, v: View): this(v){
        this.context = context
        tvMessage = v.findViewById(R.id.tvMessage)
        ivBanner = v.findViewById(R.id.ivBanner)
    }
}