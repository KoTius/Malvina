package com.kotsu.malvina.model.rest.request.classes

import com.kotsu.malvina.model.data.products.ProductType
import com.google.gson.annotations.SerializedName


data class ProductRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int = ProductType.PRODUCT
)