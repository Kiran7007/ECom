package com.example.emanditask.helpers

import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object Mapper {

    @JvmStatic
    fun getPrice(price: Double): String {
        return "₹ ${price}"
    }

}