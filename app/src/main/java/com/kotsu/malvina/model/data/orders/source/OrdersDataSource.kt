package com.kotsu.malvina.model.data.orders.source

import com.kotsu.malvina.model.data.orders.Order
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface OrdersDataSource {

    fun getOrders(): Flowable<List<Order>>

    fun getOrder(orderId: Int): Single<Order>

    fun saveOrders(orders: List<Order>)

    fun completeOrder(orderId: Int): Completable

    fun cancelOrder(orderId: Int, commentary: String): Completable

    fun updateAddress(orderId: Int, address: String): Completable

    fun updateCommentary(orderId: Int, commentary: String): Completable
}