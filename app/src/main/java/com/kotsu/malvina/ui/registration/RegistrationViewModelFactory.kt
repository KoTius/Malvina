package com.kotsu.malvina.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RegistrationViewModelFactory(

) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegistrationViewModel() as T
    }
}