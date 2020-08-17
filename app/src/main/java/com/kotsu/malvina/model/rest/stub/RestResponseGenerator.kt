package com.kotsu.malvina.model.rest.stub

import android.annotation.SuppressLint
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotsu.malvina.model.rest.request.classes.ProductRequest
import com.kotsu.malvina.model.rest.response.*
import com.kotsu.malvina.model.rest.response.classes.*
import java.io.IOException
import java.lang.reflect.Type


/**
 * This class acts as a webserver to mock data.
 * // TODO: Learn some modern solution for such task
 */
object RestResponseGenerator {

    private const val PRODUCT_ID_PIXEL2 = 1
    private const val PRODUCT_ID_COLA_LIGHT = 2

    fun logIn(login: String, password: String): LogInResponse {

        throwIfMainThread()

        val userAuthorized = authorizeUser(login, password)

        return if (userAuthorized) {
            LogInResponse("token").apply {
                isSuccess = true
            }
        } else {
            LogInResponse("token").apply {
                isSuccess = false
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun authorizeUser(login: String, password: String): Boolean {
        return login.toLowerCase() == "admin" && password.toLowerCase() == "admin"
    }

    fun getOrders(): GetOrdersResponse {
        throwIfMainThread()

        val getOrders = GetOrdersResponse(generateOrdersData())
        getOrders.isSuccess = true

        return getOrders
    }

    private fun generateOrdersData(): OrdersData {
        val apiOrders = arrayListOf<ApiOrder>()
        var apiOrder: ApiOrder
        var apiDelivery: ApiDelivery
        var apiRecipient: ApiRecipient
        val productsForOrder = generateApiOrderProducts()
        for (x in 1..5) {
            // TODO: Create more user friendly stub data (Names, addresses, descriptions etc"
            apiRecipient = ApiRecipient("John Doe", "+007", "Address")
            apiDelivery = ApiDelivery(apiRecipient, 5f)
            apiOrder = ApiOrder(
                x,
                "#code$x",
                System.currentTimeMillis(),
                15015F,
                200,
                0,
                "Some commentary $x",
                apiDelivery,
                productsForOrder
            )

            apiOrders.add(apiOrder)
        }

        return OrdersData(apiOrders)
    }

    private fun generateApiOrderProducts(): List<ApiOrderProduct> {
        val apiOrderProducts = arrayListOf<ApiOrderProduct>()
        apiOrderProducts.add(generateApiOrderProductById(PRODUCT_ID_PIXEL2))
        apiOrderProducts.add(generateApiOrderProductById(PRODUCT_ID_COLA_LIGHT))
        return apiOrderProducts
    }

    //
//    fun completeOrder(orderId: Int): String {
//    }
//
//    fun cancelOrder(orderId: Int, commentary: String): Single<CancelOrderResponse> {
//    }

    fun updateCommentaryToOrder(orderId: Int, commentary: String): BaseResponse {
        throwIfMainThread()

        return BaseResponse(true)
    }

    fun updateAddressToOrder(orderId: Int, address: String): BaseResponse {
        throwIfMainThread()

        return BaseResponse(true)
    }

    fun getStorageData(): GetStorageDataResponse {
        throwIfMainThread()

        val getStorageDataResponse = GetStorageDataResponse(generateStorageData())
        getStorageDataResponse.isSuccess = true
        return getStorageDataResponse
    }

    private fun generateStorageData(): StorageData {

        val productsRelationshipList = arrayListOf<ProductRelationship>()

        val productRelationship1 = ProductRelationship(PRODUCT_ID_PIXEL2, Available(5), Hold(0))
        val productRelationship2 = ProductRelationship(PRODUCT_ID_COLA_LIGHT, Available(25), Hold(5))
        productsRelationshipList.add(productRelationship1)
        productsRelationshipList.add(productRelationship2)

        return StorageData(productsRelationshipList)
    }

    fun getProductsByIds(productRequestJson: String): GetProductsResponse {

        val listType: Type = object: TypeToken<ArrayList<ProductRequest>>() {}.type
        val requestedProducts = Gson().fromJson<List<ProductRequest>>(productRequestJson, listType)

        val apiProducts = arrayListOf<ApiProduct>()

        for (requestedProduct in requestedProducts) {
            apiProducts.add(generateApiProductById(requestedProduct.id))
        }

        val getProductsResponse = GetProductsResponse(apiProducts)
        getProductsResponse.isSuccess = true
        return getProductsResponse
    }

    private fun generateApiProductById(productId: Int): ApiProduct {

        val apiProduct: ApiProduct

        when (productId) {
            PRODUCT_ID_PIXEL2 -> {
                apiProduct = ApiProduct(
                    PRODUCT_ID_PIXEL2,
                    1,
                    "Google Pixel 2 XL",
                    "It won't fit in your pocket",
                    15000F,
                    200,
                    "https://www.sketchappsources.com/resources/source-image/pixel-xl-mockup.jpg"
                )
            }
            PRODUCT_ID_COLA_LIGHT -> {
                apiProduct = ApiProduct(
                    PRODUCT_ID_COLA_LIGHT,
                    1,
                    "Coca-cola light",
                    "No calories at all (lie)",
                    15F,
                    500,
                    "https://www.sketchappsources.com/resources/source-image/cocacola-logo.png"
                )
            }
            else -> {
                throw IOException("Can't generate product data for Id:$productId")
            }
        }

        return apiProduct
    }

    private fun generateApiOrderProductById(productId: Int): ApiOrderProduct {

        val apiOrderProduct: ApiOrderProduct

        when (productId) {
            PRODUCT_ID_PIXEL2 -> {
                apiOrderProduct = ApiOrderProduct(
                    PRODUCT_ID_PIXEL2,
                    1,
                    "Google Pixel 2 XL",
                    1,
                    15000F,
                    200,
                    "https://www.sketchappsources.com/resources/source-image/pixel-xl-mockup.jpg"
                )
            }
            PRODUCT_ID_COLA_LIGHT -> {
                apiOrderProduct = ApiOrderProduct(
                    PRODUCT_ID_COLA_LIGHT,
                    1,
                    "Coca-cola light",
                    1,
                    15F,
                    500,
                    "https://www.sketchappsources.com/resources/source-image/cocacola-logo.png"
                )
            }
            else -> {
                throw IOException("Can't generate order product data for Id:$productId")
            }
        }

        return apiOrderProduct
    }

    private fun throwIfMainThread() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw IOException("Can't do I/O request in main thread")
        }
    }
}