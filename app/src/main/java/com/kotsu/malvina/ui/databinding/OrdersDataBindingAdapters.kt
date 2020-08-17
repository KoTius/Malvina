package com.kotsu.malvina.ui.databinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotsu.malvina.R
import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.data.products.Product
import com.kotsu.malvina.ui.adapters.OrdersAdapter
import com.kotsu.malvina.ui.adapters.OrdersProductsAdapter
import com.kotsu.malvina.util.Utils


@BindingAdapter("setOrders")
fun setOrders(ordersRv: RecyclerView, orders: List<Order>?) {
    val adapter = ordersRv.adapter as OrdersAdapter
    adapter.submitList(orders)
}

@BindingAdapter("setProducts")
fun setProducts(productsRv: RecyclerView, products: List<Product>?) {
    val adapter = productsRv.adapter as OrdersProductsAdapter
    adapter.submitList(products)
}

@BindingAdapter("setOrderStatus")
fun setStatus(textView: TextView, status: Int) {
    // TODO: add statuses
    textView.text = textView.context.getString(R.string.order_status_in_progress)
}

@BindingAdapter("setName")
fun setName(textView: TextView, name: String?) {
    val text = if (!name.isNullOrBlank()) {
        name
    } else {
        textView.context.getString(R.string.customer_name_empty)
    }

    textView.text = text
}

@BindingAdapter("setPhone")
fun setPhone(textView: TextView, phoneFormatted: String?) {
    val text = if (!phoneFormatted.isNullOrBlank()) {
        phoneFormatted
    } else {
        textView.context.getString(R.string.phone_empty)
    }

    textView.text = text
}

@BindingAdapter("setAddress")
fun setAddress(textView: TextView, address: String?) {
    val text = if (!address.isNullOrBlank()) {
        address
    } else {
        textView.context.getString(R.string.address_empty)
    }

    textView.text = text
}

@BindingAdapter("setAddressEditable")
fun setAddressEditable(textView: TextView, address: String?) {
    val text = if (!address.isNullOrBlank()) {
        address
    } else {
        textView.context.getString(R.string.add_address)
    }

    textView.text = text
}

@BindingAdapter("setCommentary")
fun setCommentary(textView: TextView, commentary: String?) {
    val visibility = if (!commentary.isNullOrBlank()) {
        textView.text = commentary
        View.VISIBLE
    } else {
        View.GONE
    }

    textView.visibility = visibility
}

@BindingAdapter("setCommentaryEditable")
fun setCommentaryEditable(textView: TextView, commentary: String?) {
    val text = if (!commentary.isNullOrBlank()) {
        commentary
    } else {
        textView.context.getString(R.string.add_commentary)
    }

    textView.text = text
}

@BindingAdapter("setOrderPrice")
fun setOrderPrice(textView: TextView, orderPrice: Float) {
    textView.text = Utils.formatPrice(textView.context, orderPrice)
}