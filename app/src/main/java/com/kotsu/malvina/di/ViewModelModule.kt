package com.kotsu.malvina.di

import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.data.storage.source.StorageDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import com.kotsu.malvina.ui.addaddress.domain.usecase.AddAddress
import com.kotsu.malvina.ui.addcommentary.domain.usecase.AddCommentary
import com.kotsu.malvina.ui.login.domain.usecase.LogIn
import com.kotsu.malvina.ui.ordercancel.domain.usecase.CancelOrder
import com.kotsu.malvina.ui.ordercomplete.domain.usecase.CompleteOrder
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder
import com.kotsu.malvina.ui.orders.domain.usecase.GetOrders
import com.kotsu.malvina.ui.storage.domain.usecase.GetStorageProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCases {

    @ActivityRetainedScoped
    @Provides
    fun provideGetOrders(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): GetOrders {
        return GetOrders(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideGetOrder(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): GetOrder {
        return GetOrder(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideCompleteOrder(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): CompleteOrder {
        return CompleteOrder(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideCancelOrder(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): CancelOrder {
        return CancelOrder(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideAddCommentary(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): AddCommentary {
        return AddCommentary(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideAddAddress(
        @AppModule.RepositoryOrders ordersRepository: OrdersDataSource,
        schedulers: Schedulers
    ): AddAddress {
        return AddAddress(
            ordersRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideGetStorageProducts(
        @AppModule.RepositoryStorage storageRepository: StorageDataSource,
        schedulers: Schedulers
    ): GetStorageProducts {
        return GetStorageProducts(
            storageRepository,
            schedulers
        )
    }

    @ActivityRetainedScoped
    @Provides
    fun provideLogIn(
        @AppModule.RepositoryAccount accountRepository: AccountDataSource,
        schedulers: Schedulers
    ): LogIn {
        return LogIn(
            accountRepository,
            schedulers
        )
    }
}