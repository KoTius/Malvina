package com.kotsu.malvina.model.data.account.source

import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.ui.login.domain.classes.LoginResult
import io.reactivex.Single


interface AccountDataSource {

    fun getAccountSingle(): Single<Account>

    fun getAccount(): Account?

    fun saveAccount(account: Account)

    fun logInSingle(login: String, password: String): Single<LoginResult>

    fun logIn(login: String, password: String): LoginResult

    fun invalidateAccount()

    fun invalidateAuthToken()
}