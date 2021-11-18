package com.kotsu.malvina.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.orders.domain.usecase.GetOrders
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class OrdersViewModel @ViewModelInject constructor(
    private val getOrders: GetOrders
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _showLoadingError = SingleLiveEvent<Int>()
    val showLoadingError: LiveData<Int>
        get() = _showLoadingError

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _emptyOrders: LiveData<Boolean> = Transformations.map(_orders) {
        it.isEmpty()
    }
    val emptyOrders: LiveData<Boolean>
        get() = _emptyOrders

    private val _showOrderDetailScreen = SingleLiveEvent<Int>()
    val showOrderDetailScreen: LiveData<Int>
        get() = _showOrderDetailScreen

    private val _manualLoginRequired = SingleLiveEvent<Void>()
    val manualLoginRequired: LiveData<Void>
        get() = _manualLoginRequired

    private var isLoading = false

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun start() {
        loadOrders(true)
    }

    fun refreshOrders() {
        loadOrders(false)
    }

    private fun loadOrders(showLoadingUi: Boolean) {

        if (isLoading) {
            return
        }

        isLoading = true

        if (showLoadingUi) {
            _showLoading.value = true
        }

        val disposable = getOrders.run()
            .subscribeBy(
                onNext = {
                    _showLoading.value = false
                    isLoading = false

                    _orders.value = it
                },
                onError = {
                    _showLoading.value = false
                    isLoading = false

                    if (it is ManualLoginRequiredException) {
                        _manualLoginRequired.call()
                    } else {
                        _showLoadingError.value = R.string.error_network_failure
                    }
                    it.printStackTrace()
                }
            )

        compositeDisposable.add(disposable)
    }

    fun onOrderSelected(orderId: Int) {
        _showOrderDetailScreen.value = orderId
    }
}