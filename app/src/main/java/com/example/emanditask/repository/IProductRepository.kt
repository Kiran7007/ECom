package com.example.emanditask.repository

import androidx.paging.DataSource
import com.example.emanditask.models.CartEntity
import com.example.emanditask.models.Data
import com.example.emanditask.networkutil.Result
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    /**
     * Fetch the data from the remote server.
     */
    suspend fun fetchRemoteProducts(): Result<List<Data>>

    /**
     * Insert the list of products in the database.
     */
    suspend fun insert(data: List<Data>): LongArray

    /**
     * Fetch the data from location database
     */
    fun fetchDataFromDB(): Flow<List<Data>>

    /**
     * Fetch the data from location database
     */
    fun fetchAllPagedDB(): DataSource.Factory<Int, Data>

}