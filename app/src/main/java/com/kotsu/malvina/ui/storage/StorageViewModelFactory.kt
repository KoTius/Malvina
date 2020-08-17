package com.kotsu.malvina.ui.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.storage.domain.usecase.GetStorageProducts


class StorageViewModelFactory(
    private val getStorageProducts: GetStorageProducts
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StorageViewModel(getStorageProducts) as T
    }
}