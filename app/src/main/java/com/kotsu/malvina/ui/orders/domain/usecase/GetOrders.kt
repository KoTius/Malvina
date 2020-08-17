package com.kotsu.malvina.ui.orders.domain.usecase

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import io.reactivex.Flowable

/**
 * Fetches the list of orders
 */
class GetOrders(
    private val ordersRepository: OrdersDataSource,
    private val schedulers: Schedulers
) {

    fun run(): Flowable<List<Order>> {
        return ordersRepository.getOrders()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}