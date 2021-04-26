package com.ecom.sample.utils

object Mapper {

    @JvmStatic
    fun getPrice(price: Double): String {
        return "₹ ${price}"
    }

}