package com.state.memo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.state.memo.R
import com.state.memo.model.Post
import com.state.memo.util.getTextBackgroundImage

class PostListAdapter private constructor(): RecyclerView.Adapter<PostItem>(){

    private lateinit var context: Context
    private lateinit var postList: ArrayList<Post>

    constructor(context: Context, postList: ArrayList<Post>): this(){
        this.context = context
        this.postList = postList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItem {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.post_item, parent, false)
        return PostItem(context!!, view)
    }

    override fun getItemCount(): Int {
        return postList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PostItem, position: Int) {

        if(postList != null && postList.isNotEmpty()){
            val post = postList[position]
            holder.tvMessage.text = post.data.text
            holder.ivBanner.setImageResource(getTextBackgroundImage())
        }
    }
}