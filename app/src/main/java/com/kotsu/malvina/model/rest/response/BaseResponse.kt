package com.kotsu.malvina.model.rest.response

import com.google.gson.annotations.SerializedName


open class BaseResponse(
    @SerializedName("success")
    var isSuccess: Boolean = false,
    @SerializedName("errors")
    var errors: List<RequestError> = arrayListOf()
)