package com.kotsu.malvina.model.rest.stub


import com.kotsu.malvina.model.rest.response.*
import io.reactivex.Single
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class StubApiService {

    fun logInSingle(login: String, password: String): Single<LogInResponse> {

        return Single.create { emitter ->

            emitter.onSuccess(RestResponseGenerator.logIn(login, password))
        }
    }

    fun logIn(login: String, password: String): Call<LogInResponse> {

        return object : Call<LogInResponse> {
            override fun enqueue(callback: Callback<LogInResponse>) {
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun clone(): Call<LogInResponse> {
                return this
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun cancel() {
            }

            override fun execute(): Response<LogInResponse> {
                return Response.success(RestResponseGenerator.logIn(login, password))
            }

            override fun request(): Request {
                return Request.Builder().build()
            }
        }
    }

    fun getOrders(): Single<GetOrdersResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.getOrders())
        }
    }

    fun completeOrder(orderId: Int): Single<CompleteOrderResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.completeOrder(orderId))
        }
    }

    fun cancelOrder(orderId: Int, commentary: String): Single<CancelOrderResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.cancelOrder(orderId, commentary))
        }
    }

    fun updateCommentaryToOrder(orderId: Int, commentary: String): Single<BaseResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.updateCommentaryToOrder(orderId, commentary))
        }
    }

    fun updateAddressToOrder(orderId: Int, address: String): Single<BaseResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.updateAddressToOrder(orderId, address))
        }
    }

    fun getStorageData(): Single<GetStorageDataResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.getStorageData())
        }
    }

    fun getProductsByIds(productsIds: String): Single<GetProductsResponse> {
        return Single.create { emitter ->
            emitter.onSuccess(RestResponseGenerator.getProductsByIds(productsIds))
        }
    }
}