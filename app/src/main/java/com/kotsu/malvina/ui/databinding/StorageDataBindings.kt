package com.kotsu.malvina.ui.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotsu.malvina.ui.adapters.StorageProductsAdapter
import com.kotsu.malvina.ui.storage.classes.StorageProduct


@BindingAdapter("setStorageProducts")
fun setStorageProducts(patientsRv: RecyclerView, products: List<StorageProduct>?) {
    val adapter = patientsRv.adapter as StorageProductsAdapter
    adapter.submitList(products)
}

