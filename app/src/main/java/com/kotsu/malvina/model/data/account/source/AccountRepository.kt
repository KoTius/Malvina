package com.kotsu.malvina.model.data.account.source

import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.ui.login.domain.classes.LoginFailure
import com.kotsu.malvina.ui.login.domain.classes.LoginNetworkError
import com.kotsu.malvina.ui.login.domain.classes.LoginResult
import com.kotsu.malvina.ui.login.domain.classes.LoginSuccess
import io.reactivex.Single
import java.lang.UnsupportedOperationException


class AccountRepository constructor(
    private val localDataSource: AccountDataSource,
    private val remoteDataSource: AccountDataSource
) : AccountDataSource {

    private var cachedAccount: Account? = null

    override fun getAccountSingle(): Single<Account> {

        if (cachedAccount != null) {
            return Single.just(cachedAccount)
        }

        return getAndCacheLocalAccount()
    }

    private fun getAndCacheLocalAccount(): Single<Account> {
        return localDataSource.getAccountSingle()
            .doOnSuccess {
                refreshCache(it)
            }
    }

    override fun getAccount(): Account? {

        if (cachedAccount != null) {
            return cachedAccount
        }

        val account = localDataSource.getAccount()

        if (account != null) {
            refreshCache(account)
        }

        return account
    }

    override fun logInSingle(login: String, password: String): Single<LoginResult> {
        return remoteDataSource.logInSingle(login, password)
            .doOnSuccess { loginResult ->
                if (loginResult is LoginSuccess) {
                    val account = Account(login, password, loginResult.authToken)
                    refreshCache(account)
                    localDataSource.saveAccount(account)
                } else if (loginResult is LoginFailure) {
                    invalidateAccount()
                }
            }
    }

    override fun logIn(login: String, password: String): LoginResult {
        val loginResult = remoteDataSource.logIn(login, password)

        when (loginResult) {
            is LoginSuccess -> {
                val account = Account(login, password, loginResult.authToken)
                refreshCache(account)
                localDataSource.saveAccount(account)
            }
            is LoginFailure -> invalidateAccount()
            is LoginNetworkError -> {
                // Do nothing at this point
            }
        }

        return loginResult
    }

    override fun invalidateAccount() {
        cachedAccount = null
        localDataSource.invalidateAccount()
    }

    override fun invalidateAuthToken() {
        cachedAccount?.authToken = null
        localDataSource.invalidateAuthToken()
    }

    private fun refreshCache(account: Account) {
        cachedAccount = account
    }

    override fun saveAccount(account: Account) {
        throw UnsupportedOperationException()
    }
}