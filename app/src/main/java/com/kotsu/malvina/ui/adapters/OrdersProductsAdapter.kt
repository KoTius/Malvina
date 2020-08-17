package com.kotsu.malvina.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotsu.malvina.databinding.ProductListItemFullBinding
import com.kotsu.malvina.databinding.ProductListItemSimpleBinding
import com.kotsu.malvina.model.data.products.Product
import java.lang.RuntimeException


class OrdersProductsAdapter(private val type: Int) :
    ListAdapter<Product, OrdersProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

    interface Listener {

        fun onProductSelected(productId: Int)
    }

    interface ClickHandler {

        fun onProductCardClick(view: View)
    }

    private var itemListener: Listener? = null

    fun setProductItemListener(listener: Listener) {
        itemListener = listener
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)

        when (holder) {
            is ViewHolderFull -> {
                holder.bind(createClickHandler(product), product)
            }
            is ViewHolderSimple -> {
                holder.bind(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return when (viewType) {
            VIEW_TYPE_FULL -> {
                ViewHolderFull(
                    ProductListItemFullBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                )
            }
            VIEW_TYPE_SIMPLE -> {
                ViewHolderSimple(
                    ProductListItemSimpleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                throw RuntimeException("Unknown viewType:$viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    private fun createClickHandler(product: Product): ClickHandler {
        return object : ClickHandler {
            override fun onProductCardClick(view: View) {
                itemListener?.onProductSelected(product.id)
            }
        }
    }

    abstract class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolderFull(
        private val binding: ProductListItemFullBinding
    ) : ProductViewHolder(binding.root) {

        fun bind(clickListener: ClickHandler, productItem: Product) {
            binding.apply {
                clickHandler = clickListener

                product = productItem

                executePendingBindings()
            }
        }
    }

    class ViewHolderSimple(
        private val binding: ProductListItemSimpleBinding
    ) : ProductViewHolder(binding.root) {

        fun bind(productItem: Product) {
            binding.apply {

                product = productItem

                executePendingBindings()
            }
        }
    }

    companion object {
        @JvmField
        val VIEW_TYPE_FULL = 1
        @JvmField
        val VIEW_TYPE_SIMPLE = 2
    }
}

private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}