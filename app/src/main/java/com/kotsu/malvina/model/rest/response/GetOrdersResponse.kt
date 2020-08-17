package com.kotsu.malvina.model.rest.response

import com.kotsu.malvina.model.rest.response.classes.ApiOrder
import com.google.gson.annotations.SerializedName


data class GetOrdersResponse(
    @SerializedName("order")
    val data: OrdersData
) : BaseResponse()

data class OrdersData(
    @SerializedName("items")
    val orders: List<ApiOrder>
)