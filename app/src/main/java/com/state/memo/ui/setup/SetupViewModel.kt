package com.state.memo.ui.setup

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.state.memo.data.network.response.AdminResponse
import com.state.memo.data.setup.SetupRepository


class SetupViewModel: ViewModel() {

    fun fetchAdmin(context: Context, fectched: ()-> Unit, failed: ()-> Unit){
        val task = SetupRepository(context).fetchAndSaveAdmin()
        task.addOnCompleteListener {
            if(task.isSuccessful){
                val document = task.result
                Log.e("Jeff", document?.id + " => " + document?.data)
                //save the admin to db:
                document?.data?.let {
                    saveAdmin(context, it)
                }
                fectched.invoke()
            }else{
                failed.invoke()
            }
        }
    }

    private fun saveAdmin(context: Context, adminResponseAsMap: Map<String, Any>){
        val gson = Gson()
        val element = gson.toJsonTree(adminResponseAsMap)
        val adminResponse = gson.fromJson(element, AdminResponse::class.java)
        adminResponse?.admin?.let {
            SetupRepository(context).saveAdmin(viewModelScope, it)
        }

    }
}