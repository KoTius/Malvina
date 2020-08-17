package com.kotsu.malvina.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.kotsu.malvina.R
import com.kotsu.malvina.ui.customview.contentloading.ContentLoadingProgressView


@BindingAdapter(value = ["showContentLoadingProgress", "showContentLoadingError"], requireAll = true)
fun bindContentLoadingStatus(contentLoadingView: ContentLoadingProgressView, loading: Boolean, error: Boolean) {
    // Set visibility
    val visible: Boolean = loading || error

    if (visible) {
        contentLoadingView.visibility = View.VISIBLE

        contentLoadingView.showProgress(loading)

        if (error) {
            val errorText = contentLoadingView.context.resources.getString(R.string.error_network_failure)
            contentLoadingView.showError(errorText)
        }
    } else {
        contentLoadingView.visibility = View.GONE
    }
}

@BindingAdapter("setOnRetryClickListener")
fun setOnRetryListener(contentLoadingView: ContentLoadingProgressView, listener: View.OnClickListener) {
    contentLoadingView.setOnClickRetryListener(listener)
}