package com.ecom.sample.data.repository

import com.ecom.sample.data.db.dao.CartDao
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val cartDao: CartDao
) : CartRepository {

    override fun fetchDataFromDB() = cartDao.fetchCarts()

    override fun clearCart() = cartDao.deleteAll()

    override fun delete(item: Cart)  = cartDao.delete(item)
}