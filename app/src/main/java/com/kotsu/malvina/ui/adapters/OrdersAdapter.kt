package com.kotsu.malvina.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotsu.malvina.databinding.OrdersItemBinding
import com.kotsu.malvina.model.data.orders.Order


class OrdersAdapter(
    private val itemListener: OrderItemListener
) : ListAdapter<Order, OrdersAdapter.ViewHolder>(OrdersDiffCallback()) {

    interface OrderItemListener {

        fun onOrderSelected(orderId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrdersItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)

        holder.bind(order)
    }

    inner class ViewHolder(
        private val binding: OrdersItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {

            with(binding) {

                this.order = order

                orderItemLayout.setOnClickListener {
                    itemListener.onOrderSelected(order.id)
                }
                executePendingBindings()
            }
        }
    }
}

private class OrdersDiffCallback : DiffUtil.ItemCallback<Order>() {

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
                && oldItem.status == newItem.status
                && oldItem.recipient == newItem.recipient
                && oldItem.products.size == newItem.products.size
    }
}