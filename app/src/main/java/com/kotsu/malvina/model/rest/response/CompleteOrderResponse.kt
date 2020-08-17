package com.kotsu.malvina.model.rest.response

import com.google.gson.annotations.SerializedName


data class CompleteOrderResponse(
    @SerializedName("order")
    val data: Data
) : BaseResponse() {

    data class Data(
        @SerializedName("id")
        val orderId: Int,
        @SerializedName("status")
        val newOrderStatus: Int
    )
}

