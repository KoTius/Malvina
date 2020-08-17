package com.kotsu.malvina.ui.registration

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


class RegistrationViewModel(

) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}