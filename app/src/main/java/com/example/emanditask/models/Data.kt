package com.example.emanditask.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Data(
    val mrp: Int,
    val price: Double,
    val product_brand_id: Int,
    val product_code: String,

    @PrimaryKey(autoGenerate = false)
    val product_id: Int,

    val product_name: String,
    val product_weight: Int,
    val product_weight_unit: String,

) : BaseObservable() {

    @get:Bindable
    @Ignore
    var updateProductQuantity: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.updateProductQuantity)
        }

}