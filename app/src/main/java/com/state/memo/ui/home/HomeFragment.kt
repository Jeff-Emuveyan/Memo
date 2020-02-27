package com.state.memo.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.state.memo.R
import com.state.memo.ui.MainActivity
import com.state.memo.data.BaseRepository
import com.state.memo.data.home.HomeRepository
import com.state.memo.util.showSnackMessage
import kotlinx.android.synthetic.main.fragment_home.*

const val  RC_SIGN_IN: Int = 44

class HomeFragment : Fragment(), PopupMenu.OnMenuItemClickListener {


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textView= root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuIcon.setOnClickListener{
            val popup = PopupMenu(context!!, it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.toolbar_menu)
            popup.show()
        }

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_writePostFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        HomeRepository(context!!).getUser(1).observe(viewLifecycleOwner,Observer {
            if (it == null){
               textView.text = "No user"
            }else{
                textView.text = "There is user"
            }

        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.sign_up ->{
                launchFirebaseAuthentication()
                return true
            }
            R.id.sign_out ->{
                homeViewModel.signOut(context!!)
                return true
            }
        }
        return true
    }


    /**
     * Uses firebase UI auth to sign up a user
     * ***/
    private fun launchFirebaseAuthentication() = startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(homeViewModel.getAuthProviders())
                .build(),
            RC_SIGN_IN)



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == com.state.memo.ui.RC_SIGN_IN) {
            handleSignIn(resultCode, data)
        }
    }


    private fun handleSignIn(resultCode : Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)

        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.e(
                MainActivity::class.java.simpleName, "name: ${user?.displayName ?: "You"}" +
                        "  ${user?.phoneNumber} " +
                        " ${user?.email} " +
                        "${user?.photoUrl?.toString()}")
            //finally save the user:
            HomeRepository(context!!).saveUser(lifecycleScope, 1, user)
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            handleSignInError(response)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleSignInError(response : IdpResponse?) {
        val view = activity?.window?.decorView?.rootView
        if (response == null){
            showSnackMessage(view!!, "Cancelled")
        }else{
            showSnackMessage(view!!, ErrorCodes.toFriendlyMessage(response.error!!.errorCode))
        }
    }

}