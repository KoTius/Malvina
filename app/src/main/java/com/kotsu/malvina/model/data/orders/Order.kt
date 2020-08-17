package com.kotsu.malvina.model.data.orders

import com.kotsu.malvina.model.data.recipients.Recipient
import com.kotsu.malvina.model.data.products.Product

//@Entity(tableName = "orders")
data class Order(
//    @ColumnInfo(name = "id")
    val id: Int,
    val code: String,
    val unixTime: Long,
    val price: Float,
    val deliveryPrice: Float,
    val weightGrams: Int,
    val status: Int,
    val recipient: Recipient,
    val products: List<Product>
) {
    val totalPrice: Float
        get() = price + deliveryPrice
}