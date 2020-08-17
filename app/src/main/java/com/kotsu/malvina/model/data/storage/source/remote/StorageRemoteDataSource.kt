package com.kotsu.malvina.model.data.storage.source.remote

import com.kotsu.malvina.model.data.storage.source.StorageDataSource
import com.kotsu.malvina.model.rest.ApiService
import com.kotsu.malvina.model.rest.mappers.StorageProductsMapper
import com.kotsu.malvina.model.rest.request.classes.ProductRequest
import com.kotsu.malvina.ui.storage.classes.StorageProduct
import com.google.gson.Gson
import com.kotsu.malvina.model.rest.stub.StubApiService
import io.reactivex.Single
import java.io.IOException


class StorageRemoteDataSource(
    private val apiService: StubApiService
) : StorageDataSource {

    override fun getProducts(): Single<List<StorageProduct>> {
        return apiService.getStorageData()
            .flatMap { getStorageDataResponse ->
                if (getStorageDataResponse.isSuccess) {

                    val requestProducts = arrayListOf<ProductRequest>()

                    for (product in getStorageDataResponse.data.productsRelationship) {
                        requestProducts.add(ProductRequest(product.productId))
                    }

                    val getProductsRequest = Gson().toJson(requestProducts)

                    apiService.getProductsByIds(getProductsRequest)
                        .flatMap {getProductsResponse ->

                            if (getProductsResponse.isSuccess) {
                                Single.just(StorageProductsMapper.fromApi(getProductsResponse.products, getStorageDataResponse.data.productsRelationship))
                            } else {
                                Single.error(IOException("Server responded with a error loading products"))
                            }
                        }
                } else {
                    Single.error(IOException("Server responded with a error loading storage data"))
                }
            }
    }
}