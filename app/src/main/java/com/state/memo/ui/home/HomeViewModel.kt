package com.state.memo.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.state.memo.data.BaseRepository
import com.state.memo.data.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    /** Choose authentication providers **/
    fun getAuthProviders() = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())


    fun signOut(context: Context){
        HomeRepository(context).signOut()
    }


    suspend fun isUserAdmin(context: Context): Boolean{
        return HomeRepository(context).isUserAdmin()
    }
}