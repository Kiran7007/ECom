package com.example.emanditask.models

data class ProductModel(
    val `data`: List<Data>,
    val msg: String,
    val paginate: Paginate,
    val status: Int
)