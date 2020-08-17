package com.kotsu.malvina.model.rest.response

import com.google.gson.annotations.SerializedName


data class GetStorageDataResponse(
    @SerializedName("data")
    val data: StorageData
) : BaseResponse()

data class StorageData(
    @SerializedName("items")
    val productsRelationship: List<ProductRelationship>
)

data class HoldProducts(
    @SerializedName("items")
    val products: List<ProductRelationship>
)

data class ProductRelationship(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("available")
    val available: Available,
    @SerializedName("hold")
    val hold: Hold
)

data class Available(
    @SerializedName("quantity")
    val count: Int
)

data class Hold(
    @SerializedName("quantity")
    val count: Int
)