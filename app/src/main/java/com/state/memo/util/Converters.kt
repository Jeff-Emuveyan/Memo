package com.state.memo.util

import androidx.room.TypeConverter
import com.state.memo.model.User

class Converters {
    @TypeConverter
    fun stringToType(value: String?): User.Type? {
        return value?.let { User.Type.valueOf(value) }
    }

    @TypeConverter
    fun typeToString(type: User.Type?): String? {
        return type?.name
    }
}