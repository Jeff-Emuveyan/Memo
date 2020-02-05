package com.state.memo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.state.memo.R
import com.state.memo.util.showSnackMessage


class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 44
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_message,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        FirebaseApp.initializeApp(this)

        //we place a listener to know when the user has signed out:
        //This will trigger anytime the user sign out. Successfully or not.
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener {
            val user = it.currentUser

            if (user == null){//user has logged out:
                //change UI for logout/no user
                Snackbar.make(window.decorView.rootView, "Done", Snackbar.LENGTH_LONG).show()

            }else{
                Log.e("User state", "There is user")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.sign_up ->{
                launchFirebaseAuthentication()
            }
            R.id.sign_out ->{
               viewModel.signOut(this@MainActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * Uses firebase UI auth to sign up a user
     * ***/
    private fun launchFirebaseAuthentication(){

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(viewModel.getAuthProviders())
                .build(),
            RC_SIGN_IN)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleSignIn(resultCode, data)
        }
    }

    private fun handleSignIn(resultCode : Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)

        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            //Log.e(MainActivity::class.java.simpleName, "name: ${user?.displayName!!}  ${user.phoneNumber}  ${user.email} ${user.photoUrl.toString()}")
            viewModel.userHasLoggedIn.value = true //set the live data
        } else {
            viewModel.userHasLoggedIn.value = false //set the live data
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            handleSignInError(response)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleSignInError(response : IdpResponse?) {
        val view = window.decorView.rootView
        if (response == null){
            showSnackMessage(view, "Cancelled")
        }else{
            showSnackMessage(view, ErrorCodes.toFriendlyMessage(response.error!!.errorCode))
        }
    }
}
