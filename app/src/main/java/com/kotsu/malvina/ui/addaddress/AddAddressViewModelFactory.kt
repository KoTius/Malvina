package com.kotsu.malvina.ui.addaddress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.addaddress.domain.usecase.AddAddress


class AddAddressViewModelFactory(
    private val orderId: Int,
    private val addAddress: AddAddress
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddAddressViewModel(orderId, addAddress) as T
    }
}