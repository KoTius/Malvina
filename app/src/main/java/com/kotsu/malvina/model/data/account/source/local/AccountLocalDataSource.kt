package com.kotsu.malvina.model.data.account.source.local

import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.room.dao.AccountDao
import com.kotsu.malvina.ui.login.domain.classes.LoginResult
import io.reactivex.Single


class AccountLocalDataSource(
    private val accountDao: AccountDao
) : AccountDataSource {

    override fun getAccountSingle(): Single<Account> {
        return accountDao.getAccountReactive()
    }

    override fun getAccount(): Account? {
        return accountDao.getAccount()
    }

    override fun saveAccount(account: Account) {
        accountDao.upsert(account)
    }

    override fun invalidateAccount() {
        accountDao.invalidateAccount();
    }

    override fun invalidateAuthToken() {
        accountDao.invalidateAuthToken()
    }

    override fun logInSingle(login: String, password: String): Single<LoginResult> {
        throw UnsupportedOperationException()
    }

    override fun logIn(login: String, password: String): LoginResult {
        throw UnsupportedOperationException()
    }
}