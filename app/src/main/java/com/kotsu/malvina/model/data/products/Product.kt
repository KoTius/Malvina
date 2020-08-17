package com.kotsu.malvina.model.data.products

/**
 * Order product
 */
data class Product(
    val id: Int,
    val type: Int,
    val name: String,
    val count: Int,
    val price: Float,
    val weightGrams: Int,
    val imageUrl: String
)