package com.state.memo.ui.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.state.memo.R
import com.state.memo.util.Repository

class MessageFragment : Fragment() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var textViewInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_message, container, false)
        textViewInfo = root.findViewById(R.id.text_info)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check if there is a user account in the database
        context?.let {
            val user =  Repository(context!!).getUser(1)

        }
    }
}