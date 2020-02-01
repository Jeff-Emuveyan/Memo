package com.state.memo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val id: Int,
                @ColumnInfo(name = "type") val type: Type,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "email") var email: String,
                @ColumnInfo(name = "password") var password: String,
                @ColumnInfo(name = "profilePicture") var profilePicture: String){

     enum class Type{
         PARENT(),
         SCHOOL()
     }
}