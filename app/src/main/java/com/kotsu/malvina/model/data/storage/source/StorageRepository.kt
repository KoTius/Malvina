package com.kotsu.malvina.model.data.storage.source

import com.kotsu.malvina.ui.storage.classes.StorageProduct
import io.reactivex.Single


class StorageRepository(
    private val remoteDataSource: StorageDataSource
) : StorageDataSource {

    override fun getProducts(): Single<List<StorageProduct>> {
        return remoteDataSource.getProducts()
    }
}