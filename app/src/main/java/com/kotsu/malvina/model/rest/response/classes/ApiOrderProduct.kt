package com.kotsu.malvina.model.rest.response.classes

import com.google.gson.annotations.SerializedName


class ApiOrderProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val count: Int,
    @SerializedName("price")
    val price: Float,
    @SerializedName("weight")
    val weightGrams: Int,
    @SerializedName("image")
    val imageUrl: String
)