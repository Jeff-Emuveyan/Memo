package com.state.memo.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.state.memo.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun getUser(id: Int): LiveData<User>

    @Query("SELECT * FROM user WHERE id LIKE :id")
    suspend fun getUserSynchronously(id: Int): User

    @Update
    suspend fun updateUser(user: User)


    @Delete
    suspend fun deleteUser(user: User)
}