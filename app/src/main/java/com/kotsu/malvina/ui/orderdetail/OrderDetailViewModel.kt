package com.kotsu.malvina.ui.orderdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class OrderDetailViewModel(
    private val orderId: Int,
    private val getOrder: GetOrder
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoadingError = MutableLiveData<Boolean>()
    val isLoadingError: LiveData<Boolean>
        get() = _isLoadingError

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    private val _showOrderCompleteScreen = SingleLiveEvent<Int>()
    val showOrderCompleteScreen: LiveData<Int>
        get() = _showOrderCompleteScreen

    private val _showCancelOrderScreen = SingleLiveEvent<Int>()
    val showCancelOrderScreen: LiveData<Int>
        get() = _showCancelOrderScreen

    private val _showAddCommentaryScreen = SingleLiveEvent<Order>()
    val showAddCommentaryScreen: LiveData<Order>
        get() = _showAddCommentaryScreen

    private val _showAddAddressScreen = SingleLiveEvent<Order>()
    val showAddAddressScreen: LiveData<Order>
        get() = _showAddAddressScreen

    private val _copyPhoneToClipboard = SingleLiveEvent<String>()
    val copyPhoneToClipboard: LiveData<String>
        get() = _copyPhoneToClipboard

    private val _showStartDialScreen = SingleLiveEvent<String>()
    val showStartDialScreen: LiveData<String>
        get() = _showStartDialScreen

    private val _showNoPhoneNumberProvided = SingleLiveEvent<Void>()
    val showNoPhoneNumberProvided: LiveData<Void>
        get() = _showNoPhoneNumberProvided

    private val _manualLoginRequired = SingleLiveEvent<Void>()
    val manualLoginRequired: LiveData<Void>
        get() = _manualLoginRequired

    private val compositeDisposable = CompositeDisposable()

    init {
        loadOrderDetail()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun loadOrderDetail() {

        _isLoading.value = true
        _isLoadingError.value = false

        val disposable = getOrder.run(orderId)
            .subscribeBy(
                onSuccess = {
                    _isLoading.value = false
                    _order.value = it
                },
                onError = {
                    _isLoading.value = false
                    it.printStackTrace()
                    if (it is ManualLoginRequiredException) {
                        _manualLoginRequired.call()
                    } else {
                        _isLoadingError.value = true
                    }
                }
            )

        compositeDisposable.add(disposable)
    }

    fun retry() {
        compositeDisposable.clear()
        loadOrderDetail()
    }

    fun orderDelivered() {
        _showOrderCompleteScreen.value = orderId
    }

    fun copyPhoneToClipboard(): Boolean {
        val phone: String? = order.value?.recipient?.phoneFormatted

        if (!phone.isNullOrBlank()) {
            _copyPhoneToClipboard.value = phone
        } else {
            _showNoPhoneNumberProvided.call()
        }

        return true
    }

    fun onCancelOrderClicked() {
        _showCancelOrderScreen.value = orderId
    }

    fun onStartDialClicked() {

        val phone: String? = order.value?.recipient?.phoneFormatted

        if (!phone.isNullOrBlank()) {
            _showStartDialScreen.value = phone
        } else {
            _showNoPhoneNumberProvided.call()
        }
    }

    fun onCommentaryClicked() {
        _showAddCommentaryScreen.value = _order.value
    }

    fun onAddressClicked() {
        _showAddAddressScreen.value = _order.value
    }
}