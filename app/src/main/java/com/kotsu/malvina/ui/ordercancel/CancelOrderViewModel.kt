package com.kotsu.malvina.ui.ordercancel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.ordercancel.domain.usecase.CancelOrder
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class CancelOrderViewModel @ViewModelInject constructor(
    private val cancelOrder: CancelOrder
) : ViewModel() {

    private var _orderId: Int = 0

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _popUpToOrdersScreen = SingleLiveEvent<Void>()
    val popUpToOrdersScreen: LiveData<Void>
        get() = _popUpToOrdersScreen

    private val _showMessage = SingleLiveEvent<Int>()
    val showMessage: LiveData<Int>
        get() = _showMessage

    private val _manualLoginRequired = SingleLiveEvent<Void>()
    val manualLoginRequired: LiveData<Void>
        get() = _manualLoginRequired

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun start(orderId: Int) {
        this._orderId = orderId
    }

    fun cancelOrder(commentary: String) {

        _isLoading.value = true

        val disposable = cancelOrder.run(_orderId, commentary)
            .doAfterTerminate {
                _isLoading.value = false
            }
            .subscribeBy (
                onComplete = {
                    _popUpToOrdersScreen.call()
                },
                onError = {
                    _isLoading.value = false
                    if (it is ManualLoginRequiredException) {
                        _manualLoginRequired.call()
                    } else {
                        _showMessage.value = R.string.error_network_failure
                    }
                }
            )

        compositeDisposable.add(disposable)
    }
}