package com.state.memo.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "user")
data class User(@PrimaryKey val id: Int,
                @ColumnInfo(name = "type") val type: Type,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "email") var email: String,
                @ColumnInfo(name = "profilePicture") var profilePicture: String?){

    constructor(): this(1, Type.PARENT, "", "", "")


     enum class Type{
         PARENT(),
         SCHOOL()
     }
}