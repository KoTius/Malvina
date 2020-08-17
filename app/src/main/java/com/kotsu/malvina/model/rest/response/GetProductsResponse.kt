package com.kotsu.malvina.model.rest.response

import com.kotsu.malvina.model.rest.response.classes.ApiProduct


data class GetProductsResponse(
    val products: List<ApiProduct>
) : BaseResponse()
