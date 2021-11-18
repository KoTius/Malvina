package com.kotsu.malvina.model.rest

import android.content.Context
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.login.domain.classes.LoginSuccess
import dagger.hilt.android.EntryPointAccessors
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
 * @throws ManualLoginRequiredException if token refreshing failed
 *
 * @param appContext Context required for Hilt's EntryPoint so we can get AccountRepository instance which
 * is not possible at instance creation because of circular dependency:
 * AccountRepository required for AuthTokenInterceptor, AuthTokenInterceptor required for RetrofitHelper,
 * RetrofitHelper required for AccountRepository
 *
 * @return server response on original request
 */
class AuthTokenInterceptor(
    private val appContext: Context
) : Interceptor {

    interface AuthTokenInterceptorEntryPoint {

        fun accountRepository(): AccountDataSource
    }

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

        val account = getAccountRepository().getAccount()

        if (account?.authToken == null) {
            throw ManualLoginRequiredException("Account or auth token is null")
        }

        originalRequest = addAuthorizationHeader(originalRequest, account.authToken!!)

        var response = chain.proceed(originalRequest)

        val isAuthTokenRefreshRequired = response.code() == AUTHORIZATION_ERROR_RESPONSE_CODE

        if (isAuthTokenRefreshRequired) {
            getAccountRepository().invalidateAuthToken()
            // auto login for token refresh.
            val loginResult = getAccountRepository().logIn(account.login, account.password)

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

    private fun getAccountRepository(): AccountDataSource {
        val hiltEntry = EntryPointAccessors.fromApplication(appContext, AuthTokenInterceptorEntryPoint::class.java)
        return hiltEntry.accountRepository()
    }

    companion object {
        private const val AUTHORIZATION_ERROR_RESPONSE_CODE = 401
    }
}