package com.kotsu.malvina.ui.addcommentary.domain.usecase

import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import io.reactivex.Completable


class AddCommentary(
    private val ordersRepository: OrdersDataSource,
    private val schedulers: Schedulers
) {

    fun run(orderId: Int, commentary: String): Completable {
        return ordersRepository.updateCommentary(orderId, commentary)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}