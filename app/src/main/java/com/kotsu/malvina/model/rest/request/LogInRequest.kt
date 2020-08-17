package com.kotsu.malvina.model.rest.request

import com.google.gson.annotations.SerializedName


data class LogInRequest(
    @SerializedName("email")
    val login: String,
    @SerializedName("password")
    val password: String
)