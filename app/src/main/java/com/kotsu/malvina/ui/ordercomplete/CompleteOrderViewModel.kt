package com.kotsu.malvina.ui.ordercomplete

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.orderdetail.domain.usecase.GetOrder
import com.kotsu.malvina.ui.ordercomplete.domain.usecase.CompleteOrder
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class CompleteOrderViewModel @ViewModelInject constructor(
    private val getOrder: GetOrder,
    private val completeOrder: CompleteOrder
) : ViewModel() {

    private var _orderId: Int = 0

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoadingError = MutableLiveData<Boolean>()
    val isLoadingError: LiveData<Boolean>
        get() = _isLoadingError

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    private val _showMessage = SingleLiveEvent<Int>()
    val showMessage: LiveData<Int>
        get() = _showMessage

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    private val _popUpToOrdersScreen = SingleLiveEvent<Void>()
    val popUpToOrdersScreen: LiveData<Void>
        get() = _popUpToOrdersScreen

    private val _manualLoginRequired = SingleLiveEvent<Void>()
    val manualLoginRequired: LiveData<Void>
        get() = _manualLoginRequired

    private val _isOrderCompleted = MutableLiveData<Boolean>().also {
        it.value = false
    }
    val isOrderCompleted: LiveData<Boolean>
        get() = _isOrderCompleted

    private var _isOrderCompletionInProgress = false

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun start(orderId: Int) {

        if (_isLoading.value == true || _orderId == orderId) {
            return
        }

        _orderId = orderId

        loadOrderDetail()
    }

    fun retry() {
        compositeDisposable.clear()
        loadOrderDetail()
    }

    fun completeOrder() {

        if (_isOrderCompletionInProgress) {
            _showMessage.value = R.string.in_progress
            return
        }

        _showProgressBar.value = true
        _isOrderCompletionInProgress = true

        val disposable = completeOrder.run(_orderId)
            .doAfterTerminate {
                _showProgressBar.value = false
                _isOrderCompletionInProgress = false
            }
            .subscribeBy (
                onComplete = {
                    _isOrderCompleted.value = true
                },
                onError = {
                    if (it is ManualLoginRequiredException) {
                        _manualLoginRequired.call()
                    } else {
                        _showMessage.value = R.string.error_completing_order
                    }
                }
            )

        compositeDisposable.add(disposable)
    }

    fun navigateToOrderListScreen() {
        _popUpToOrdersScreen.call()
    }

    fun onBackPressed(): Boolean {

        if (_isOrderCompleted.value == true) {
            _popUpToOrdersScreen.call()
            return true
        }

        return false
    }

    private fun loadOrderDetail() {

        _isLoading.value = true
        _isLoadingError.value = false

        val disposable = getOrder.run(_orderId)
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
}