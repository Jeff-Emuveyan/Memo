package com.state.memo.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.state.memo.data.database.room.dao.AdminDao
import com.state.memo.data.database.room.dao.UserDao
import com.state.memo.model.Admin
import com.state.memo.model.User
import com.state.memo.util.Converters

@Database(entities = [User::class, Admin::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun adminDao(): AdminDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}