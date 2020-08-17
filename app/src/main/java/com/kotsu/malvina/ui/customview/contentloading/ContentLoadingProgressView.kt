package com.kotsu.malvina.ui.customview.contentloading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.kotsu.malvina.R


class ContentLoadingProgressView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val errorLayout: ViewGroup
    private val errorText: TextView
    private val retry: Button
    private val progress: ContentLoadingProgressBar

    init {
        val root = View.inflate(context, R.layout.content_loading_progress_view, this)

        errorLayout = root.findViewById(R.id.content_loading_error_layout)
        errorText = root.findViewById(R.id.content_loading_view_error_text)
        retry = root.findViewById(R.id.content_loading_view_retry_button)
        progress = root.findViewById(R.id.content_loading_view_progress)
    }

    fun showError(text: CharSequence) {
        errorText.text = text

        errorLayout.visibility = VISIBLE
        progress.hide()
    }

    fun showProgress(show: Boolean) {
        errorLayout.visibility = GONE
        if (show) {
            progress.show()
        } else {
            progress.hide()
        }
    }

    fun setOnClickRetryListener(listener: OnClickListener) {
        retry.setOnClickListener(listener)
    }
}