package com.kotsu.malvina.model.rest.response.classes

import com.google.gson.annotations.SerializedName


data class ApiRecipient(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phoneFormatted: String,
    @SerializedName("address")
    val address: String
//    @SerializedName("commentary")
//    val commentary: String
)