package com.ecom.sample.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("productId"),
            childColumns = arrayOf("id"),
        )
    ],
)
data class Cart(
    @PrimaryKey(autoGenerate = false)
    var id: Int,

    @ColumnInfo(name = "mrp")
    val mrp: Int,

    @ColumnInfo(name = "productName")
    var productName: String,

    @ColumnInfo(name = "productQuantity")
    var productQuantity: String,

    @ColumnInfo(name = "productPrice")
    var productPrice: Double
)