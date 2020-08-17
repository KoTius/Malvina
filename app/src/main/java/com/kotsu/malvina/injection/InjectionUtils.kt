package com.kotsu.malvina.injection

import android.content.Context
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.data.account.source.AccountRepository
import com.kotsu.malvina.model.data.account.source.local.AccountLocalDataSource
import com.kotsu.malvina.model.data.account.source.remote.AccountRemoteDataSource
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.data.orders.source.OrdersRepository
import com.kotsu.malvina.model.data.orders.source.local.OrdersLocalDataSource
import com.kotsu.malvina.model.data.orders.source.remote.OrdersRemoteDataSource
import com.kotsu.malvina.model.data.storage.source.StorageDataSource
import com.kotsu.malvina.model.data.storage.source.StorageRepository
import com.kotsu.malvina.model.data.storage.source.remote.StorageRemoteDataSource
import com.kotsu.malvina.model.rest.RetrofitHelper
import com.kotsu.malvina.model.room.AppDatabase
import com.kotsu.malvina.model.schedulers.AppSchedulers
import com.kotsu.malvina.model.schedulers.Schedulers
import com.kotsu.malvina.ui.MainViewModelFactory
import com.kotsu.malvina.ui.addaddress.AddAddressViewModelFactory
import com.kotsu.malvina.ui.addaddress.domain.usecase.AddAddress
import com.kotsu.malvina.ui.addcommentary.AddCommentaryViewModelFactory
import com.kotsu.malvina.ui.addcommentary.domain.usecase.AddCommentary
import com.kotsu.malvina.ui.login.LoginViewModelFactory
import com.kotsu.malvina.ui.ordercomplete.CompleteOrderViewModelFactory
import com.kotsu.malvina.ui.login.domain.usecase.LogIn
import com.kotsu.malvina.ui.ordercancel.CancelOrderViewModelFactory
import com.kotsu.malvina.ui.ordercomplete.domain.usecase.CompleteOrder
import com.kotsu.malvina.ui.orderdetail.OrderDetailViewModelFactory
import com.kotsu.malvina.ui.orders.OrdersViewModelFactory
import com.kotsu.malvina.ui.ordercancel.domain.usecase.CancelOrder
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder
import com.kotsu.malvina.ui.orders.domain.usecase.GetOrders
import com.kotsu.malvina.ui.registration.RegistrationViewModelFactory
import com.kotsu.malvina.ui.storage.StorageViewModelFactory
import com.kotsu.malvina.ui.storage.domain.usecase.GetStorageProducts
import com.kotsu.malvina.ui.template.TemplateViewModelFactory


object InjectionUtils {

    fun provideTemplateViewModelFactory(context: Context): TemplateViewModelFactory {
        return TemplateViewModelFactory()
    }

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory()
    }

    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        return LoginViewModelFactory(provideLogInUseCase(context))
    }

    fun provideRegistrationViewModelFactory(): RegistrationViewModelFactory {
        return RegistrationViewModelFactory()
    }

    fun provideOrdersViewModelFactory(
        context: Context
    ): OrdersViewModelFactory {
        return OrdersViewModelFactory(
            provideGetOrdersUseCase(context)
        )
    }

    fun provideOrderDetailViewModelFactory(context: Context, orderId: Int): OrderDetailViewModelFactory {
        return OrderDetailViewModelFactory(
            orderId,
            provideGetOrderUseCase(context))
    }

    fun provideCancelOrderViewModelFactory(context: Context, orderId: Int): CancelOrderViewModelFactory {
        return CancelOrderViewModelFactory(
            orderId,
            provideCancelOrderUseCase(context))
    }

    fun provideAddCommentaryViewModelFactory(context: Context, orderId: Int): AddCommentaryViewModelFactory {
        return AddCommentaryViewModelFactory(
            orderId,
            provideAddCommentaryUseCase(context)
        )
    }

    fun provideAddAddressViewModelFactory(context: Context, orderId: Int): AddAddressViewModelFactory {
        return AddAddressViewModelFactory(
            orderId,
            provideAddAddressUseCase(context)
        )
    }

    fun provideCompleteOrderViewModelFactory(context: Context, orderId: Int): CompleteOrderViewModelFactory {
        return CompleteOrderViewModelFactory(
            orderId,
            provideGetOrderUseCase(context),
            provideCompleteOrderUseCase(context)
        )
    }

    fun provideStorageViewModelFactory(context: Context): StorageViewModelFactory {
        return StorageViewModelFactory(
            provideGetStorageProductsUseCase()
        )
    }

    fun provideAccountRepository(context: Context): AccountDataSource {
        return AccountRepository.getInstance(
            AccountLocalDataSource.getInstance(AppDatabase.getInstance(context).accountDao()),
            AccountRemoteDataSource.getInstance(provideRetrofitHelper().stubApiService)
        )
    }

    fun provideRetrofitHelper() = RetrofitHelper.getInstance()

    private fun provideOrdersRepository(context: Context): OrdersDataSource {
        return OrdersRepository.getInstance(
            OrdersLocalDataSource.getInstance(),
            OrdersRemoteDataSource.getInstance(provideRetrofitHelper().stubApiService)
        )
    }

    private fun provideStorageRepository(): StorageDataSource {
        return StorageRepository.getInstance(
            StorageRemoteDataSource.getInstance(provideRetrofitHelper().stubApiService)
        )
    }

    private fun provideGetOrdersUseCase(context: Context): GetOrders {
        return GetOrders(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideGetOrderUseCase(context: Context): GetOrder {
        return GetOrder(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideCompleteOrderUseCase(context: Context): CompleteOrder {
        return CompleteOrder(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideCancelOrderUseCase(context: Context): CancelOrder {
        return CancelOrder(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideAddCommentaryUseCase(context: Context): AddCommentary {
        return AddCommentary(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideAddAddressUseCase(context: Context): AddAddress {
        return AddAddress(
            provideOrdersRepository(context),
            provideSchedulers()
        )
    }

    private fun provideLogInUseCase(context: Context): LogIn {
        return LogIn(
            provideAccountRepository(context),
            provideSchedulers()
        )
    }

    private fun provideGetStorageProductsUseCase(): GetStorageProducts {
        return GetStorageProducts(
            provideStorageRepository(),
            provideSchedulers()
        )
    }

    private fun provideSchedulers(): Schedulers = AppSchedulers.getInstance()
}