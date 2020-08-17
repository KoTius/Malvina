package com.kotsu.malvina.model.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.kotsu.malvina.model.data.account.Account
import io.reactivex.Single


@Dao
abstract class AccountDao : BaseDao<Account>() {

    @Query("SELECT * FROM account LIMIT 1")
    abstract fun getAccountReactive(): Single<Account>

    @Query("SELECT * FROM account LIMIT 1")
    abstract fun getAccount(): Account?

    @Query("UPDATE account SET auth_token = null")
    abstract fun invalidateAuthToken()

    @Query("DELETE from account")
    abstract fun invalidateAccount()
}