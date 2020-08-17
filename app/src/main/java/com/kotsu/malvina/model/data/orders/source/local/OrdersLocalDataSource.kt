package com.kotsu.malvina.model.data.orders.source.local

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class OrdersLocalDataSource : OrdersDataSource {

    override fun getOrders(): Flowable<List<Order>> {
        return Flowable.error(Exception("Not Implemented"))
    }

    override fun getOrder(orderId: Int): Single<Order> {
        return Single.error(Exception("Not Implemented"))
    }

    override fun saveOrders(orders: List<Order>) {
    }

    override fun completeOrder(orderId: Int): Completable {
        // TODO: change order status to completed
        return Completable.complete()
    }

    override fun updateCommentary(orderId: Int, commentary: String): Completable {
        // TODO:
        return Completable.complete()
    }
    override fun updateAddress(orderId: Int, address: String): Completable {
        // TODO:
        return Completable.complete()
    }

    override fun cancelOrder(orderId: Int, commentary: String): Completable {
        // TODO: change order status to canceled
        return Completable.complete()
    }
}