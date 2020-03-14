package com.state.memo.ui.createpost

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

import com.state.memo.R
import com.state.memo.model.Data
import com.state.memo.util.showSnackMessage
import kotlinx.android.synthetic.main.create_post_fragment.*
import kotlinx.coroutines.*

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

        viewModel.postStatus.observe(viewLifecycleOwner, Observer {
            if(it == true){
                showSnackMessage(activity!!.window!!.decorView, "Done!")
                postingUIState(false)
            }else if(it == false){
                showSnackMessage(activity!!.window!!.decorView, "Failed to upload, try again")
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postingUIState(false)

        postButton.setOnClickListener{
            it.visibility = View.GONE
            val data = Data(editText.text.toString().trim(), imagePath, videoPath)
            postData(lifecycleScope, data)
        }
    }


    private fun postData(coroutineScope: CoroutineScope, data: Data){
        coroutineScope.launch(Dispatchers.Main) {
            postingUIState(true)
            if(viewModel collectAndValidateData data){
                withContext(Dispatchers.IO){
                    viewModel.post(context!!, data)
                }
            }else{
                postingUIState(false)
                showSnackMessage(activity!!.window!!.decorView, "Post cannot be empty...")
            }
        }
    }


    fun postingUIState(state: Boolean){
        if(state){
            progressBar.visibility = View.VISIBLE
            postButton.visibility = View.GONE
        }else{
            progressBar.visibility = View.GONE
            postButton.visibility = View.VISIBLE
            editText.text.clear()
        }
    }

}
