package com.kotsu.malvina.model.data.account.source.remote

import com.kotsu.malvina.model.data.account.Account
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.rest.ApiService
import com.kotsu.malvina.model.rest.mappers.LoginErrorsMapper
import com.kotsu.malvina.model.rest.stub.StubApiService
import com.kotsu.malvina.ui.login.domain.classes.LoginFailure
import com.kotsu.malvina.ui.login.domain.classes.LoginNetworkError
import com.kotsu.malvina.ui.login.domain.classes.LoginResult
import com.kotsu.malvina.ui.login.domain.classes.LoginSuccess
import com.kotsu.malvina.util.Utils
import io.reactivex.Single
import java.lang.Exception


class AccountRemoteDataSource private constructor(
    private val apiService: StubApiService
) : AccountDataSource {

    override fun logInSingle(login: String, password: String): Single<LoginResult> {
        return apiService.logInSingle(login, password)
                .flatMap {
                    if (it.isSuccess) {
                        Single.just(LoginSuccess(it.authToken))
                    } else {
                        Single.just(LoginFailure(LoginErrorsMapper.fromApi(it.errors)))
                    }
                }
                .onErrorResumeNext {
                    it.printStackTrace()
                    Single.just(LoginNetworkError())
                }
    }

    override fun logIn(login: String, password: String): LoginResult {

        var loginResult: LoginResult

        try {

            val call = apiService.logIn(login, password)

            val callResponse = call.execute()

            if (callResponse.isSuccessful) {

                val loginResponseBody = callResponse.body()!!

                if (loginResponseBody.isSuccess) {
                    log("Login succeeded. Setting auth token to retrofit:${loginResponseBody.authToken}")
                    loginResult = LoginSuccess(loginResponseBody.authToken)
                } else {
                    log("Login Failure error from server")
                    loginResult = LoginFailure(LoginErrorsMapper.fromApi(loginResponseBody.errors))
                }
            } else {
                log("Login network error")
                loginResult = LoginNetworkError()
            }
        } catch (e: Exception) {
            loginResult = LoginNetworkError()
        }

        return loginResult
    }

    override fun getAccountSingle(): Single<Account> {
        throw UnsupportedOperationException()
    }

    override fun getAccount(): Account? {
        throw UnsupportedOperationException()
    }

    override fun saveAccount(account: Account) {
        throw UnsupportedOperationException()
    }

    override fun invalidateAccount() {
        throw UnsupportedOperationException()
    }

    override fun invalidateAuthToken() {
        throw UnsupportedOperationException()
    }

    private fun log(text: String) {
        Utils.log("AccountRemote -> $text")
    }

    companion object {

        private var INSTANCE: AccountRemoteDataSource? = null

        @JvmStatic
        fun getInstance(apiService: StubApiService) =
            INSTANCE ?: synchronized(AccountRemoteDataSource::class.java) {
                INSTANCE ?: AccountRemoteDataSource(apiService)
                    .also {
                        INSTANCE = it
                    }
            }
    }
}