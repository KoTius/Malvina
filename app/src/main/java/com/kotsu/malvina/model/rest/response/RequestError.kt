package com.kotsu.malvina.model.rest.response

import com.google.gson.annotations.SerializedName


class RequestError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("details")
    val details: String
) {
    companion object {

        @JvmField
        val ERROR_CODE_INVALID_CREDENTIALS = 1
    }
}