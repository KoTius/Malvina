package com.kotsu.malvina.ui.databinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kotsu.malvina.R
import com.kotsu.malvina.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("app:isGone")
fun setViewVisibility(view: View, isGone: Boolean?){
    if (isGone != null && isGone) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:fabVisibility")
fun toggleFabVisibility(fab: FloatingActionButton, show: Boolean) {
    if (show) {
        fab.show()
    } else {
        fab.hide()
    }
}

@BindingAdapter("imageFromUrl")
fun loadImageFromUrl(view: ImageView, imageUrl: String?) {

    val options = RequestOptions()
    @Suppress("CheckResult")
    options.placeholder(R.drawable.image_placeholder)

    Glide.with(view.context)
        .load(imageUrl)
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("setProductPrice")
fun setProductPrice(textView: TextView, productPrice: Float) {
    textView.text = Utils.formatPrice(textView.context, productPrice)
}

@BindingAdapter("setElapsedTime")
fun setElapsedTime(textView: TextView, startAt: Long) {
    val elapsedSeconds = (System.currentTimeMillis() / 1000) - startAt
    val et = Utils.formatElapsedTime(textView.context, elapsedSeconds)

    val text = textView.context.getString(R.string.elapsed_time, et)
    textView.text = text
}