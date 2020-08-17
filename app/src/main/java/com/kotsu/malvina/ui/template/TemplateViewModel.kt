package com.kotsu.malvina.ui.template

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


class TemplateViewModel(

) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}