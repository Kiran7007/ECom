package com.ecom.sample.data.repository

import androidx.paging.DataSource
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.models.Result
import com.ecom.sample.data.db.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    /**
     * Fetch the data from the remote server.
     */
    suspend fun fetchRemoteProducts(): Result<List<Product>>

    /**
     * Insert the list of products in the database.
     */
    suspend fun insert(data: List<Product>): LongArray

    /**
     * Insert the list of cart in the database.
     */
    suspend fun insertCartList(data: List<Cart>): LongArray

    /**
     * Fetch the data from location database
     */
    fun fetchDataFromDB(): Flow<List<Product>>

    /**
     * Fetch the data from location database
     */
    fun fetchAllPagedDB(): DataSource.Factory<Int, Product>

    fun clearAllCartItems()
}