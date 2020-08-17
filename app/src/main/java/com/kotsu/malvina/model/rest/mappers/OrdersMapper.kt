package com.kotsu.malvina.model.rest.mappers

import com.kotsu.malvina.model.data.orders.Order
import com.kotsu.malvina.model.rest.response.classes.ApiOrder


class OrdersMapper {

    companion object {

        @JvmStatic
        fun fromApi(apiOrders: List<ApiOrder>): List<Order> {
            val orders = arrayListOf<Order>()

            for (apiOrder in apiOrders) {
                orders.add(fromApi(apiOrder))
            }

            return orders
        }

        @JvmStatic
        fun fromApi(apiOrder: ApiOrder): Order {
            val recipient = RecipientMapper.fromApi(apiOrder.delivery.recipient, apiOrder.commentary)
            return Order(
                apiOrder.id,
                apiOrder.code,
                apiOrder.unixTime,
                apiOrder.price,
                apiOrder.delivery.price,
                apiOrder.weightGrams,
                apiOrder.status,
                recipient,
                OrderProductsMapper.fromApi(apiOrder.products)
            )
        }
    }
}