package com.kotsu.malvina.model.rest

import com.kotsu.malvina.model.rest.response.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("user/auth")
    @Headers("No-Auth: true")
    fun logInSingle(
        @Field("email") login: String,
        @Field("password") password: String
    ): Single<LogInResponse>

    @FormUrlEncoded
    @POST("user/auth")
    @Headers("No-Auth: true")
    fun logIn(
        @Field("email") login: String,
        @Field("password") password: String
    ): Call<LogInResponse>

    @GET("order/list")
    fun getOrders(): Single<GetOrdersResponse>

    @FormUrlEncoded
    @POST("order/done")
    fun completeOrder(@Field("orderId") orderId: Int): Single<CompleteOrderResponse>

    @FormUrlEncoded
    @POST("order/reject")
    fun cancelOrder(
        @Field("orderId") orderId: Int,
        @Field("comment") commentary: String
    ): Single<CancelOrderResponse>

    @FormUrlEncoded
    @POST("order/save-comment")
    fun updateCommentaryToOrder(
        @Field("orderId") orderId: Int,
        @Field("comment") commentary: String
    ): Single<BaseResponse>

    @FormUrlEncoded
    @POST("order/save-delivery-address")
    fun updateAddressToOrder(
        @Field("orderId") orderId: Int,
        @Field("address") address: String
    ): Single<BaseResponse>

    @POST("warehouse/data")
    fun getStorageData(): Single<GetStorageDataResponse>

    @FormUrlEncoded
    @POST("product/by-id")
    fun getProductsByIds(@Field("data") productsIds: String): Single<GetProductsResponse>
}