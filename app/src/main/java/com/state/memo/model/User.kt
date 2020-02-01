package com.state.memo.model

data class User(val id: Int, val type: Type, var email: String, var password: String, var profilePicture: String){

     enum class Type{
         PARENT(),
         SCHOOL()
     }
}