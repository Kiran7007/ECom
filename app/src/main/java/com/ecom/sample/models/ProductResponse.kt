package com.ecom.sample.models

import com.ecom.sample.data.db.entity.Product

data class ProductResponse(
    val `data`: List<Product>,
    val msg: String,
    val paginate: Paginate,
    val status: Int
)