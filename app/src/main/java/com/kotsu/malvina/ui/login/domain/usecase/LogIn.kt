package com.kotsu.malvina.ui.login.domain.usecase

import android.util.Base64
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import com.kotsu.malvina.ui.login.domain.classes.LoginResult
import io.reactivex.Single
import java.nio.charset.Charset

/**
 * Tries to authorize with remote server.
 */
class LogIn(
    private val accountRepository: AccountDataSource,
    private val schedulers: Schedulers
) {

    fun run(login: String, password: String): Single<LoginResult> {

        val encodedPassword = Base64.encodeToString(password.toByteArray(Charset.forName("UTF-8")), Base64.DEFAULT)

        return accountRepository.logInSingle(login, encodedPassword)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}