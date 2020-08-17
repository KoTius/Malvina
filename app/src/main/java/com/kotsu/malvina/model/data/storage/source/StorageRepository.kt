package com.kotsu.malvina.model.data.storage.source

import com.kotsu.malvina.ui.storage.classes.StorageProduct
import io.reactivex.Single


class StorageRepository private constructor(
    private val remoteDataSource: StorageDataSource
) : StorageDataSource {

    override fun getProducts(): Single<List<StorageProduct>> {
        return remoteDataSource.getProducts()
    }

    companion object {

        private var INSTANCE: StorageRepository? = null

        @JvmStatic
        fun getInstance(
            remoteDataSource: StorageDataSource
        ) =
            INSTANCE ?: synchronized(StorageRepository::class.java) {
                INSTANCE ?: StorageRepository(remoteDataSource)
                    .also {
                        INSTANCE = it
                    }
            }
    }
}