package com.kotsu.malvina.ui.ordercomplete.domain.usecase

import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import io.reactivex.Completable


class CompleteOrder(
    private val ordersRepository: OrdersDataSource,
    private val schedulers: Schedulers
) {

    fun run(orderId: Int): Completable {
        return ordersRepository.completeOrder(orderId)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}