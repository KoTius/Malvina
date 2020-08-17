package com.kotsu.malvina.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.model.room.dao.AccountDao

@Database(entities = [
    Account::class
],
    version = 1,
    exportSchema = false)
//@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase =
            INSTANCE
                ?: synchronized(AppDatabase::class.java) {
                    INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "malvina.db")
                        .fallbackToDestructiveMigration()
                        .build()
                        .also {
                            INSTANCE = it
                        }
                }
    }
}