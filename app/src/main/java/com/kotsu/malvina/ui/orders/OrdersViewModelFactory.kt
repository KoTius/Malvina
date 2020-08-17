package com.kotsu.malvina.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.orders.domain.usecase.GetOrders


class OrdersViewModelFactory(
    private val getOrders: GetOrders
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(getOrders) as T
    }
}