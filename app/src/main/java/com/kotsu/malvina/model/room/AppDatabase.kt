package com.kotsu.malvina.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.model.room.dao.AccountDao

@Database(entities = [
    Account::class
],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
}