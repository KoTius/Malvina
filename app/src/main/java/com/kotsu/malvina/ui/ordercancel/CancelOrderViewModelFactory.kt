package com.kotsu.malvina.ui.ordercancel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.ordercancel.domain.usecase.CancelOrder


class CancelOrderViewModelFactory(
    private val orderId: Int,
    private val cancelOrder: CancelOrder
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CancelOrderViewModel(orderId, cancelOrder) as T
    }
}