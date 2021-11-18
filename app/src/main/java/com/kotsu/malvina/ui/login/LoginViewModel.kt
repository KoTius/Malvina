package com.kotsu.malvina.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotsu.malvina.R
import com.kotsu.malvina.ui.login.domain.classes.*
import com.kotsu.malvina.ui.login.domain.usecase.LogIn
import com.kotsu.malvina.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class LoginViewModel @ViewModelInject constructor(
    private val logIn: LogIn
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showMessage = SingleLiveEvent<Int>()
    val showMessage: LiveData<Int>
        get() = _showMessage

    private val _popFromBackStack = SingleLiveEvent<Void>()
    val popFromBackStack: LiveData<Void>
        get() = _popFromBackStack

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun attemptSignIn(login: String, password: String) {

        _isLoading.value = true

        val disposable = logIn.run(login, password)
            .subscribeBy {

                when (it) {
                    is LoginSuccess -> {
                        _popFromBackStack.call()
                    }
                    is LoginFailure -> {
                        proceedLoginErrors(it.errors)
                    }
                    is LoginNetworkError -> {
                        _showMessage.value = R.string.error_network_failure
                    }
                }

                _isLoading.value = false
            }

        compositeDisposable.add(disposable)
    }

    private fun proceedLoginErrors(errors: List<LoginError>) {
        for (error in errors) {
            when (error) {
                is LoginInvalidCredentials -> {
                    _showMessage.value = R.string.error_credentials_invalid
                }
                else -> {
                    _showMessage.value = R.string.error_unknown
                }
            }
        }
    }
}