package com.kotsu.malvina.model.rest.response.classes

import com.google.gson.annotations.SerializedName


data class ApiOrder(
    @SerializedName("id")
    val id: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("createdAt")
    val unixTime: Long,
    @SerializedName("sum")
    val price: Float,
    @SerializedName("weight")
    val weightGrams: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("comment")
    val commentary: String,
    @SerializedName("delivery")
    val delivery: ApiDelivery,
    @SerializedName("products")
    val products: List<ApiOrderProduct>
)

data class ApiDelivery(
    @SerializedName("recipient")
    val recipient: ApiRecipient,
    @SerializedName("price")
    val price: Float
)