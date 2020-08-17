package com.kotsu.malvina

import android.app.Application
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.model.data.account.source.AccountDataSource


class MyApplication : Application() {

    /**
     * Do not expose this object in this class.
     * If you need accountRepository - use InjectorUtils.
     */
    private lateinit var accountRepository: AccountDataSource

    override fun onCreate() {
        super.onCreate()

        initRetrofitHelperAndAccountDataSource()
    }

    /**
     * Retrofit helper requires account data source for Authorization header manipulation
     */
    private fun initRetrofitHelperAndAccountDataSource() {
        accountRepository = InjectionUtils.provideAccountRepository(this)
        val retrofitHelper = InjectionUtils.provideRetrofitHelper()
        retrofitHelper.setAccountDataSource(accountRepository)
    }
}