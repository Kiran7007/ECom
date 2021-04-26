package com.ecom.sample.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("product_id")
    @ColumnInfo(name = "productId")
    val productId: Int,

    @SerializedName("mrp")
    @ColumnInfo(name = "mrp")
    val mrp: Int,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Double,

    @SerializedName("product_brand_id")
    @ColumnInfo(name = "productBrandId")
    val productBrandId: Int,

    @SerializedName("product_code")
    @ColumnInfo(name = "productCode")
    val productCode: String,

    @SerializedName("product_name")
    @ColumnInfo(name = "productName")
    val productName: String,

    @SerializedName("product_weight")
    @ColumnInfo(name = "productWeight")
    val productWeight: Int,

    @SerializedName("product_weight_unit")
    @ColumnInfo(name = "productWeightUnit")
    val productWeightUnit: String,

    ) : BaseObservable() {

    @get:Bindable
    @Ignore
    var updateProductQuantity: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.updateProductQuantity)
        }
}