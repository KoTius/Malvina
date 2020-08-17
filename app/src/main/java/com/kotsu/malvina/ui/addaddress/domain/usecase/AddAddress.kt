package com.kotsu.malvina.ui.addaddress.domain.usecase

import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import io.reactivex.Completable


class AddAddress(
    private val ordersRepository: OrdersDataSource,
    private val schedulers: Schedulers
) {

    fun run(orderId: Int, address: String): Completable {
        return ordersRepository.updateAddress(orderId, address)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}