package com.kotsu.malvina.ui.addaddress

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.addaddress.domain.usecase.AddAddress
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class AddAddressViewModel @ViewModelInject constructor(
    private val addAddress: AddAddress
) : ViewModel() {

    private var _orderId: Int = 0

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _popUpScreen = SingleLiveEvent<Void>()
    val popUpScreen: LiveData<Void>
        get() = _popUpScreen

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

    fun addAddress(address: String) {

        _isLoading.value = true

        val disposable = addAddress.run(_orderId, address)
            .doAfterTerminate {
                _isLoading.value = false
            }
            .subscribeBy (
                onComplete = {
                    _popUpScreen.call()
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