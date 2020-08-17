package com.kotsu.malvina.model.rest

import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotsu.malvina.model.rest.stub.StubApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitHelper {

    private val authTokenInterceptor = AuthTokenInterceptor()

    val stubApiService = StubApiService()

    private var _apiService: ApiService? = null
    val apiService: ApiService
        get() {
            if (_apiService == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(authTokenInterceptor)
                    .build()

                _apiService = Retrofit.Builder()
                    // TODO: add real base url here
                    .baseUrl("https://kotsu.com/api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return _apiService!!
        }

    fun setAccountDataSource(accountRepository: AccountDataSource) {
        authTokenInterceptor.accountRepository = accountRepository
    }

    companion object {

        private var INSTANCE: RetrofitHelper? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(RetrofitHelper::class.java) {
                INSTANCE
                    ?: RetrofitHelper()
                        .also { INSTANCE = it }
            }
    }
}