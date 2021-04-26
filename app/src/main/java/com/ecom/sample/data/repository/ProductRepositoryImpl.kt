package com.ecom.sample.data.repository

import androidx.paging.DataSource
import com.ecom.sample.data.db.ProductsDao
import com.ecom.sample.models.Product
import com.ecom.sample.data.remote.ApiService
import com.ecom.sample.models.Result
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productsDao: ProductsDao
) : ProductRepository {

    override suspend fun fetchRemoteProducts(): Result<List<Product>> {
        return try {
            val response = apiService.fetchPeoples()
            if (response.isSuccessful) {
                Result.Success(response.body()?.data ?: emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insert(data: List<Product>) = productsDao.insert(data)

    override fun fetchDataFromDB(): Flow<List<Product>> = productsDao.fetchProducts()

    override fun fetchAllPagedDB(): DataSource.Factory<Int, Product> = productsDao.getAllPaged()
}