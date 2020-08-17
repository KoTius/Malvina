package com.kotsu.malvina.ui.storage.classes


class StorageProduct(
    val id: Int,
    val type: Int,
    val name: String,
    val description: String,
    val countAvailable: Int,
    val countHold: Int,
    val price: Float,
    val weightGrams: Int,
    val imageUrl: String
) {
    override fun hashCode(): Int {
        return (type.toString() + id.toString()).hashCode()
    }
}