package com.kotsu.malvina.ui.orderdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder


class OrderDetailViewModelFactory(
    private val orderId: Int,
    private val getOrder: GetOrder
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderDetailViewModel(
            orderId,
            getOrder
        ) as T
    }
}