package com.state.memo.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.state.memo.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): LiveData<User>
}