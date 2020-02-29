package com.state.memo.data.setup

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.state.memo.BuildConfig
import com.state.memo.data.BaseRepository
import com.state.memo.data.network.NetworkHelper
import com.state.memo.model.Admin
import kotlinx.coroutines.*

class SetupRepository(var cxt: Context): BaseRepository(cxt){


    fun fetchAndSaveAdmin(): Task<DocumentSnapshot>{
        val flavour = BuildConfig.FLAVOR
        //fetch all admin emails for the current flavour
        return  NetworkHelper.fetchAdmin(flavour)
    }

    fun saveAdmin(coroutineScope: CoroutineScope, admin: Admin){
        coroutineScope.launch(Dispatchers.IO) {
            db.adminDao().saveAdmin(admin)
        }
    }
}