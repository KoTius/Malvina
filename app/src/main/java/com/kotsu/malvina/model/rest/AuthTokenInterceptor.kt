package com.kotsu.malvina.model.rest

import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.rest.exception.AccountDataSourceNotProvidedException
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.login.domain.classes.LoginSuccess
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * 1. Adds Authorization header provided by accountRepository to each request which aren't marked with "No-Auth" header
 * 2. Proceeds original request and checks response
 * 2.1 If response has 401 error(wrong Auth Token), takes credentials accountRepository and proceeds login request
 * 2.2 If login request successful fetches new token from login response and provides it to accountRepository
 * 2.3 Adds new token to original request and proceeds it again
 *
 * @throws AccountDataSourceNotProvidedException if accountRepository is not provided
 * @throws ManualLoginRequiredException if token refreshing failed
 *
 * @return server response on original request
 */
class AuthTokenInterceptor : Interceptor {

    var accountRepository: AccountDataSource? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        return performRequestWithAuthChecks(chain)
    }

    private fun performRequestWithAuthChecks(chain: Interceptor.Chain): Response {

        var originalRequest = chain.request()

        // Requests which doesn't require authorization process must be marked with No-Auth header in retrofit service
        val isAuthRequired = originalRequest.header("No-Auth") == null

        if (!isAuthRequired) {
            return chain.proceed(originalRequest)
        }

        if (accountRepository == null) {
            throw AccountDataSourceNotProvidedException("You must provide account data source to fetching auth token")
        }

        val account = accountRepository!!.getAccount()

        if (account?.authToken == null) {
            throw ManualLoginRequiredException("Account or auth token is null")
        }

        originalRequest = addAuthorizationHeader(originalRequest, account.authToken!!)

        var response = chain.proceed(originalRequest)

        val isAuthTokenRefreshRequired = response.code() == AUTHORIZATION_ERROR_RESPONSE_CODE

        if (isAuthTokenRefreshRequired) {
            accountRepository!!.invalidateAuthToken()
            // auto login for token refresh.
            val loginResult = accountRepository!!.logIn(account.login, account.password)

            if (loginResult is LoginSuccess) {
                val newAuthToken = loginResult.authToken

                // update Authorization header for original request
                originalRequest = addAuthorizationHeader(originalRequest, newAuthToken)

                response = chain.proceed(originalRequest)
            } else {
                // TODO: proceed case when login result is network error
                throw ManualLoginRequiredException("Error refreshing auth token")
            }
        }

        return response
    }

    private fun addAuthorizationHeader(request: Request, authToken: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
    }

    companion object {
        private val AUTHORIZATION_ERROR_RESPONSE_CODE = 401
    }
}