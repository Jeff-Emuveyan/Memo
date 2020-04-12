package com.state.memo.ui.createpost
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.state.memo.R
import com.state.memo.model.Data
import com.state.memo.model.MediaFileUploadStatus
import com.state.memo.ui.MainActivityViewModel
import com.state.memo.util.CreatePostUIState
import com.state.memo.util.mediaFileUploadStatus
import com.state.memo.util.showSnackMessage
import com.state.memo.util.toast
import kotlinx.android.synthetic.main.create_post_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreatePostFragment : Fragment() {


    private lateinit var viewModel: CreatePostViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var imagePath: String? = null
    private var videoPath: String? = null
    private val imagePickerRequest = 33;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_post_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreatePostViewModel::class.java)
        mainActivityViewModel  = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)

        //checks to know when a post has been uploaded successfully:
        mainActivityViewModel.postStatus.observe(viewLifecycleOwner, Observer {
            if(it == true){
                postingUIState(CreatePostUIState.SUCCESS)
                mainActivityViewModel.postStatus.value = null//reset it
            }else if(it == false){
                postingUIState(CreatePostUIState.FAILED)
                showSnackMessage(activity!!.window!!.decorView, "Failed to upload, try again")
                mainActivityViewModel.postStatus.value = null//reset it
            }
        })

        //check to know if a media file is being uploaded:
        mainActivityViewModel.mediaFileUploadStatus.observe(viewLifecycleOwner, Observer {
            if(it == MediaFileUploadStatus.UPLOADING){
                //Go back to home fragment so that user can see the upload process:
                findNavController().navigate(R.id.action_writePostFragment_to_navigation_home)
            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postingUIState(CreatePostUIState.DEFAULT)
        imageSelectedUIState(false)

        editText.doOnTextChanged { _, _, _, _ -> tvPostSuccessful.visibility = View.GONE }

        postButton.setOnClickListener{
            it.visibility = View.GONE
            val data = Data(editText.text.toString().trim(), imagePath, videoPath)
            postData(lifecycleScope, data)
        }

        ivPickImage.setOnClickListener {
            toast(getString(R.string.please_wait))
            selectImage()
        }

        ivCancelImage.setOnClickListener{
            imageSelectedUIState(false)
        }

        ivBackButton.setOnClickListener{
            findNavController().navigate(R.id.action_writePostFragment_to_navigation_home)
        }
    }


    private fun postData(coroutineScope: CoroutineScope, data: Data){
        coroutineScope.launch(Dispatchers.Main) {
            postingUIState(CreatePostUIState.IN_PROGRESS)
            if(viewModel collectAndValidateData data){
                withContext(Dispatchers.IO){
                    mainActivityViewModel.post(context!!, data)
                }
            }else{
                postingUIState(CreatePostUIState.DEFAULT)
                showSnackMessage(activity!!.window!!.decorView, "Post cannot be empty...")
            }
        }
    }


    /**
     * 'true' means the fragment is in progress of posting data
     * 'false' means the fragment is not doing any work
     * **/
    private fun postingUIState(state: CreatePostUIState){

        when(state){
            CreatePostUIState.IN_PROGRESS -> {
                progressBar.visibility = View.VISIBLE
                postButton.visibility = View.GONE
                tvPostSuccessful.visibility = View.GONE
            }
            CreatePostUIState.DEFAULT ->{
                progressBar.visibility = View.GONE
                postButton.visibility = View.VISIBLE
                editText.text.clear()
                tvPostSuccessful.visibility = View.GONE
            }
            CreatePostUIState.SUCCESS ->{
                progressBar.visibility = View.GONE
                postButton.visibility = View.VISIBLE
                editText.text.clear()
                tvPostSuccessful.visibility = View.VISIBLE
            }

            CreatePostUIState.FAILED ->{
                progressBar.visibility = View.GONE
                postButton.visibility = View.VISIBLE
                tvPostSuccessful.visibility = View.GONE
            }

        }
    }

    private fun imageSelectedUIState(state: Boolean){
        if(state){
            ivCancelImage.visibility = View.VISIBLE
            tvImageLabel.text = getString(R.string.image_selected_true)
        }else{
            ivCancelImage.visibility = View.GONE
            tvImageLabel.text = getString(R.string.image_selected_default)
            ivPickImage.setImageResource(R.drawable.ic_picture)
            imagePath = null
        }
    }


    private fun selectImage(){
        val intent = Intent(context, FilePickerActivity::class.java)
        intent.putExtra(FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true).setShowImages(true).setShowVideos(false)
                .enableImageCapture(true).setMaxSelection(1).setSkipZeroSizeFiles(false)
                .build()
        )
        startActivityForResult(intent, imagePickerRequest)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == imagePickerRequest){//after the user selects an image:
                val imageFiles: ArrayList<MediaFile>? = data?.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)
                ivPickImage.setImageBitmap(viewModel.getBitmapFromResult(context!!, imageFiles))
                imagePath = viewModel.getFilePathFromResult(context!!, imageFiles)
                imageSelectedUIState(true)
            }
        }
    }

}
