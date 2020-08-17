package com.kotsu.malvina.model.data.storage.source

import com.kotsu.malvina.ui.storage.classes.StorageProduct
import io.reactivex.Single


interface StorageDataSource {

    fun getProducts(): Single<List<StorageProduct>>
}