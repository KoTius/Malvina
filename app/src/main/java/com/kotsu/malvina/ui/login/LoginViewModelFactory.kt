package com.kotsu.malvina.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.login.domain.usecase.LogIn


class LoginViewModelFactory(
    private val logInUseCase: LogIn
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(logInUseCase) as T
    }
}