package com.kotsu.malvina.model.rest.mappers

import com.kotsu.malvina.model.rest.response.ProductRelationship
import com.kotsu.malvina.model.rest.response.classes.ApiProduct
import com.kotsu.malvina.ui.storage.classes.StorageProduct


class StorageProductsMapper {

    companion object {

        @JvmStatic
        fun fromApi(apiProducts: List<ApiProduct>, productRelationship: List<ProductRelationship>): List<StorageProduct> {
            val products = arrayListOf<StorageProduct>()

            for (apiProduct in apiProducts) {

                val relationship = productRelationship.find {
                    it.productId == apiProduct.id
                }

                if (relationship != null) {
                    products.add(fromApi(apiProduct, relationship.available.count, relationship.hold.count))
                }
            }

            return products
        }

        @JvmStatic
        fun fromApi(apiProduct: ApiProduct, countAvailable: Int, countHold: Int): StorageProduct {
            return StorageProduct(
                apiProduct.id,
                apiProduct.type,
                apiProduct.name,
                apiProduct.description,
                countAvailable,
                countHold,
                apiProduct.price,
                apiProduct.weightGrams,
                apiProduct.imageUrl
            )
        }
    }
}