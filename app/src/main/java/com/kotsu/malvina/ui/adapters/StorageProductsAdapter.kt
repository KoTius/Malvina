package com.kotsu.malvina.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotsu.malvina.databinding.ProductListItemStorageBinding
import com.kotsu.malvina.ui.storage.classes.StorageProduct


class StorageProductsAdapter() :
    ListAdapter<StorageProduct, StorageProductsAdapter.StorageProductViewHolder>(ProductDiffCallback()) {

    interface Listener {

        fun onProductSelected(productId: Int)
    }

    interface ClickHandler {

        fun onProductCardClick(view: View)
    }

    private var itemListener: Listener? = null

    override fun onBindViewHolder(holder: StorageProductViewHolder, position: Int) {
        val product = getItem(position)

        holder.bind(createClickHandler(product), product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageProductViewHolder {
        return StorageProductViewHolder(
            ProductListItemStorageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    class StorageProductViewHolder(
        private val binding: ProductListItemStorageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: StorageProductsAdapter.ClickHandler, storageProduct: StorageProduct) {
            binding.apply {
                clickHandler = clickListener

                product = storageProduct

                executePendingBindings()
            }
        }
    }

    private fun createClickHandler(product: StorageProduct): ClickHandler {
        return object : ClickHandler {
            override fun onProductCardClick(view: View) {
                itemListener?.onProductSelected(product.id)
            }
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<StorageProduct>() {

        override fun areItemsTheSame(oldItem: StorageProduct, newItem: StorageProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StorageProduct, newItem: StorageProduct): Boolean {
            return oldItem.countAvailable == newItem.countHold
                    || oldItem.countHold == newItem.countHold
        }
    }
}