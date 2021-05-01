package com.ecom.sample.data.repository

import com.ecom.sample.data.db.entity.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun fetchDataFromDB(): Flow<List<Cart>>
    fun clearCart()
    fun delete(item: Cart)
}