package com.state.memo.ui.post

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.state.memo.R
import com.state.memo.model.Data
import com.state.memo.model.Post
import com.state.memo.util.PostStatus
import com.state.memo.util.Repository
import com.state.memo.util.showSnackMessage
import kotlinx.android.synthetic.main.create_post_fragment.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreatePostFragment : Fragment() {


    private lateinit var viewModel: CreatePostViewModel
    private var imagePath: String? = null
    private var videoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_post_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreatePostViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        postButton.setOnClickListener{

            val textMessage = editText.text.toString()
            val data = Data(textMessage, imagePath, videoPath)

            lifecycleScope.launch {
                postData(context!!, data)
            }
        }
    }


    suspend fun postData(context: Context, data: Data){
        viewModel.post(context, data){
            when (it){
                PostStatus.INVALID_INPUT -> showSnackMessage(activity!!.window!!.decorView, "Invalid Input")
                PostStatus.SUCCESSFUL -> showSnackMessage(activity!!.window!!.decorView, "Done!")
                PostStatus.FAILED -> showSnackMessage(activity!!.window!!.decorView, "Failed to upload, try again")
            }
        }
    }
}
