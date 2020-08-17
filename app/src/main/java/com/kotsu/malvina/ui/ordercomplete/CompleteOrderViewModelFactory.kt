package com.kotsu.malvina.ui.ordercomplete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.ordercomplete.domain.usecase.CompleteOrder
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder


class CompleteOrderViewModelFactory(
    private val orderId: Int,
    private val getOrder: GetOrder,
    private val completeOrder: CompleteOrder
    ) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompleteOrderViewModel(
            orderId,
            getOrder,
            completeOrder
        ) as T
    }
}