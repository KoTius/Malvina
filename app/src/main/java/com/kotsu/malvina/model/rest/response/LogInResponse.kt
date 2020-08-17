package com.kotsu.malvina.model.rest.response

import com.google.gson.annotations.SerializedName


data class LogInResponse(
    @SerializedName("token")
    val authToken: String
) : BaseResponse()