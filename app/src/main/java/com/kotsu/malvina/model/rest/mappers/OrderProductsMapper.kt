package com.kotsu.malvina.model.rest.mappers

import com.kotsu.malvina.model.data.products.Product
import com.kotsu.malvina.model.rest.response.classes.ApiOrderProduct


class OrderProductsMapper {

    companion object {

        @JvmStatic
        fun fromApi(apiProducts: List<ApiOrderProduct>): List<Product> {
            val products = arrayListOf<Product>()

            for (apiProduct in apiProducts) {
                products.add(fromApi(apiProduct))
            }

            return products
        }

        @JvmStatic
        fun fromApi(apiProduct: ApiOrderProduct): Product {
            return Product(
                apiProduct.id,
                apiProduct.type,
                apiProduct.name,
                apiProduct.count,
                apiProduct.price,
                apiProduct.weightGrams,
                apiProduct.imageUrl
            )
        }
    }
}