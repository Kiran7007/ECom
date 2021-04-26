package com.ecom.sample.data.repository

import androidx.paging.DataSource
import com.ecom.sample.models.Result
import com.ecom.sample.models.Product
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
     * Fetch the data from location database
     */
    fun fetchDataFromDB(): Flow<List<Product>>

    /**
     * Fetch the data from location database
     */
    fun fetchAllPagedDB(): DataSource.Factory<Int, Product>

}