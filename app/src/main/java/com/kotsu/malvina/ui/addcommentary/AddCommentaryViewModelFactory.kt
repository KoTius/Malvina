package com.kotsu.malvina.ui.addcommentary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotsu.malvina.ui.addcommentary.domain.usecase.AddCommentary


class AddCommentaryViewModelFactory(
    private val orderId: Int,
    private val addCommentary: AddCommentary
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddCommentaryViewModel(orderId, addCommentary) as T
    }
}