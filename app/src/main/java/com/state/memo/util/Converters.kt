package com.state.memo.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.state.memo.model.User

class Converters {

    @TypeConverter
    fun typeToString(type: User.Type?): String? {
        return type?.name
    }

    @TypeConverter
    fun stringToType(value: String?): User.Type? {
        return value?.let { User.Type.valueOf(value) }
    }


    @TypeConverter
    fun listToString(list: List<String>): String = Gson().toJson(list)


    @TypeConverter
    fun stringToArrayList(string: String): List<String>{
        val objects = Gson().fromJson(string,  Array<String>::class.java)
        return objects.toList()
    }
}