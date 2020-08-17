package com.kotsu.malvina.ui.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class TemplateViewModelFactory(

) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TemplateViewModel() as T
    }
}