package com.example.emanditask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class CartEntity (
    var mrp: Int,
    var price: Double,
    var product_brand_id: Int,
    var product_code: String,

    @PrimaryKey(autoGenerate = false)
    var product_id: Int,

    var product_name: String,
    var product_weight: Int,
    var product_weight_unit: String,
    var product_quantity: String
)