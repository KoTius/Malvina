package com.kotsu.malvina.ui.registration

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


class RegistrationViewModel @ViewModelInject constructor(

) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}