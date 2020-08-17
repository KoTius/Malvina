package com.kotsu.malvina.model.rest.response.classes

import com.google.gson.annotations.SerializedName


data class ApiProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("weight")
    val weightGrams: Int,
    @SerializedName("image")
    val imageUrl: String
)