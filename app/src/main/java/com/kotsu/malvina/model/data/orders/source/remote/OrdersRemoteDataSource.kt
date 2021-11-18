package com.kotsu.malvina.model.data.orders.source.remote

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.rest.ApiService
import com.kotsu.malvina.model.rest.mappers.OrdersMapper
import com.kotsu.malvina.model.rest.stub.StubApiService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.IOException
import java.lang.UnsupportedOperationException


class OrdersRemoteDataSource(
    private val apiService: StubApiService
) : OrdersDataSource {

    override fun getOrders(): Flowable<List<Order>> {

        return apiService.getOrders()
            .flatMapPublisher {
                if (it.isSuccess) {
                    val orders = OrdersMapper.fromApi(it.data.orders)
                    Flowable.just(orders)
                } else {
                    Flowable.error(IOException("Server responded with a error loading orders"))
                }
            }
    }

    override fun getOrder(orderId: Int): Single<Order> {
        return Single.error(NotImplementedError("Can't find order with id:$orderId"))
    }

    override fun completeOrder(orderId: Int): Completable {
        return apiService.completeOrder(orderId)
            .ignoreElement()
    }

    override fun cancelOrder(orderId: Int, commentary: String): Completable {
        return apiService.cancelOrder(orderId, commentary)
            .ignoreElement()
    }

    override fun updateAddress(orderId: Int, address: String): Completable {
        return apiService.updateAddressToOrder(orderId, address)
            .ignoreElement()
    }

    override fun updateCommentary(orderId: Int, commentary: String): Completable {
        return apiService.updateCommentaryToOrder(orderId, commentary)
            .ignoreElement()
    }

    override fun saveOrders(orders: List<Order>) {
        throw UnsupportedOperationException()
    }
}