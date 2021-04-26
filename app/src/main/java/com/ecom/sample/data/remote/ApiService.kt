package com.ecom.sample.data.remote

import com.ecom.sample.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * ApiService is responsible to define end points and its responses.
 */
interface ApiService {

    @GET("get-products")
    suspend fun fetchPeoples(): Response<ProductResponse>
}