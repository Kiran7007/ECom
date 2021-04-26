package com.ecom.sample.models

data class ProductResponse(
    val `data`: List<Product>,
    val msg: String,
    val paginate: Paginate,
    val status: Int
)