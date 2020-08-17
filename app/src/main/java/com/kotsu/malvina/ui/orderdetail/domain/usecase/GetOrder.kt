package com.kotsu.malvina.ui.orderdetail.domain.usecase

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import io.reactivex.Single


class GetOrder(
    private val ordersRepository: OrdersDataSource,
    private val schedulers: Schedulers
) {

    fun run(orderId: Int): Single<Order> {
        return ordersRepository.getOrder(orderId)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}