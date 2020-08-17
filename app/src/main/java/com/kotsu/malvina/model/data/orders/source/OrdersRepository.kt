package com.kotsu.malvina.model.data.orders.source

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.util.Utils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

/**
 * Manages local (as room) and remote (as rest api) data sources
 */
class OrdersRepository private constructor(
    private val localDataSource: OrdersDataSource,
    private val remoteDataSource: OrdersDataSource
) : OrdersDataSource {

    private var cachedOrders: ArrayList<Order> = arrayListOf()

    override fun getOrders(): Flowable<List<Order>> {
        return getAndSaveRemoteOrders()
            .onErrorResumeNext { throwable: Throwable ->
                if (throwable !is ManualLoginRequiredException) {
                    getLocalOrders()
                } else {
                    // If remote data source responds with ManualLoginRequiredException we do not load
                    // data from local source
                    Flowable.error(throwable)
                }
            }
    }

    private fun getAndSaveRemoteOrders(): Flowable<List<Order>> {
        return remoteDataSource.getOrders()
            .doOnNext {
                cachedOrders.clear()
                cachedOrders.addAll(it)

                localDataSource.saveOrders(it)
            }
            .doOnError {
                it.printStackTrace()
                log(it.toString())
            }
    }

    private fun getLocalOrders(): Flowable<List<Order>> {
        return localDataSource.getOrders()
    }

    override fun getOrder(orderId: Int): Single<Order> {
        val cachedOrder = cachedOrders.find {
            it.id == orderId
        }

        if (cachedOrder != null) {
            return Single.just(cachedOrder)
        }

        return getRemoteOrder(orderId)
    }

    private fun getRemoteOrder(orderId: Int): Single<Order> {
        return remoteDataSource.getOrder(orderId)
    }

    override fun saveOrders(orders: List<Order>) {
        throw UnsupportedOperationException("Only for internal use")
    }

    override fun completeOrder(orderId: Int): Completable {
        return remoteDataSource.completeOrder(orderId)
            .andThen(updateLocalOrderCompleted(orderId))
    }

    override fun cancelOrder(orderId: Int, commentary: String): Completable {
        return remoteDataSource.cancelOrder(orderId, commentary)
            .andThen(updateLocalOrderCanceled(orderId, commentary))
    }

    override fun updateAddress(orderId: Int, address: String): Completable {
        return remoteDataSource.updateAddress(orderId, address)
            .andThen(updateLocalOrderAddress(orderId, address))
    }

    override fun updateCommentary(orderId: Int, commentary: String): Completable {
        return remoteDataSource.updateCommentary(orderId, commentary)
            .andThen(updateLocalOrderCommentary(orderId, commentary))
    }

    private fun updateLocalOrderCanceled(orderId: Int, commentary: String): Completable {

        val canceledOrder = cachedOrders.find {
            it.id == orderId
        }

        cachedOrders.remove(canceledOrder)

        return localDataSource.cancelOrder(orderId, commentary)
    }

    private fun updateLocalOrderCompleted(orderId: Int): Completable {

        val completedOrder = cachedOrders.find {
            it.id == orderId
        }

        cachedOrders.remove(completedOrder)

        return localDataSource.completeOrder(orderId)
    }

    private fun updateLocalOrderCommentary(orderId: Int, commentary: String): Completable {

        val cachedOrder = cachedOrders.find {
            it.id == orderId
        }

        cachedOrder?.recipient?.commentary = commentary

        return localDataSource.updateCommentary(orderId, commentary)
    }

    private fun updateLocalOrderAddress(orderId: Int, address: String): Completable {

        val cachedOrder = cachedOrders.find {
            it.id == orderId
        }

        cachedOrder?.recipient?.address = address

        return localDataSource.updateAddress(orderId, address)
    }

    private fun log(text: String) {
        Utils.log("OrdersRepository -> $text")
    }

    companion object {

        private var INSTANCE: OrdersRepository? = null

        @JvmStatic
        fun getInstance(
            localDataSource: OrdersDataSource,
            remoteDataSource: OrdersDataSource
        ) =
            INSTANCE ?: synchronized(OrdersRepository::class.java) {
                INSTANCE ?: OrdersRepository(localDataSource, remoteDataSource)
                    .also {
                        INSTANCE = it
                    }
            }
    }
}