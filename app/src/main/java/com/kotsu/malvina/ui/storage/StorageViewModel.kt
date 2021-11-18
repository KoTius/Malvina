package com.kotsu.malvina.ui.storage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.model.rest.exception.ManualLoginRequiredException
import com.kotsu.malvina.ui.storage.classes.StorageProduct
import com.kotsu.malvina.ui.storage.domain.usecase.GetStorageProducts
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class StorageViewModel @ViewModelInject constructor(
    private val getStorageProducts: GetStorageProducts
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _showLoadingError = SingleLiveEvent<Int>()
    val showLoadingError: LiveData<Int>
        get() = _showLoadingError

    private val _products = MutableLiveData<List<StorageProduct>>()
    val products: LiveData<List<StorageProduct>>
        get() = _products

    private val _emptyProducts: LiveData<Boolean> = Transformations.map(_products) {
        it.isEmpty()
    }
    val emptyProducts: LiveData<Boolean>
        get() = _emptyProducts

    private val _manualLoginRequired = SingleLiveEvent<Void>()
    val manualLoginRequired: LiveData<Void>
        get() = _manualLoginRequired

    private val compositeDisposable = CompositeDisposable()

    init {
        loadStorageData(true)
    }

    fun refreshStorage() {
        loadStorageData(false)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun loadStorageData(showLoadingUi: Boolean) {

        if (showLoadingUi) {
            _showLoading.value = true
        }

        val disposable = getStorageProducts.run()
            .subscribeBy(
                onSuccess = {
                    _showLoading.value = false

                    _products.value = it.products
                },
                onError = {
                    _showLoading.value = false

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
}