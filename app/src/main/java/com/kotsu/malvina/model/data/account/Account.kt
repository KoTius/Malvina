package com.kotsu.malvina.model.data.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @ColumnInfo(name = "long")
    val login: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "auth_token")
    var authToken: String?
) {
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    var id: String = "single_account"

    override fun toString(): String {
        return "Login:$login token:$authToken"
    }
}