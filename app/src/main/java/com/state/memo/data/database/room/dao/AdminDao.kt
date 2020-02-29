package com.state.memo.data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.state.memo.model.Admin

@Dao
interface AdminDao {

    @Insert
    suspend fun saveAdmin(admin: Admin)

    @Query("SELECT * FROM admin WHERE id LIKE :id")
    suspend fun getAdminSynchronously(id: Int): Admin
}