package com.state.memo.ui.createpost

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        postButton.setOnClickListener{
            val data = Data(editText.text.toString(), imagePath, videoPath)
            postData(lifecycleScope, data)
        }
    }


    private fun postData(coroutineScope: CoroutineScope, data: Data){
        coroutineScope.launch(Dispatchers.Main) {
            val task = withContext(Dispatchers.IO){
                viewModel.post(context!!, data)
            }
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<DocumentReference>) {
        task.addOnCompleteListener{
            if(it.isSuccessful){
                showSnackMessage(activity!!.window!!.decorView, "Done!")
            }else{
                showSnackMessage(activity!!.window!!.decorView, "Failed to upload, try again")
            }
        }
    }



}
