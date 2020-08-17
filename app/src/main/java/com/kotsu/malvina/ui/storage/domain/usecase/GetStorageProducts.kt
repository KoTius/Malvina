package com.kotsu.malvina.ui.storage.domain.usecase

import com.kotsu.malvina.model.data.storage.source.StorageDataSource
import com.kotsu.malvina.model.schedulers.Schedulers
import com.kotsu.malvina.ui.storage.classes.StorageUIModel
import io.reactivex.Single


class GetStorageProducts(
    private val storageRepository: StorageDataSource,
    private val schedulers: Schedulers
) {

    fun run(): Single<StorageUIModel> {
        return storageRepository.getProducts()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .flatMap {
                Single.just(StorageUIModel(it))
            }
    }
}