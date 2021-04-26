package com.example.emanditask.repository

import androidx.paging.DataSource
import com.example.emanditask.dtatabase.ProductsDao
import com.example.emanditask.models.CartEntity
import com.example.emanditask.models.Data
import com.example.emanditask.models.ProductModel
import com.example.emanditask.networkutil.ApiService
import com.example.emanditask.networkutil.Result
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val apiService: ApiService,
    private val productsDao: ProductsDao
) : IProductRepository {

    override suspend fun fetchRemoteProducts(): Result<List<Data>> {
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

    override suspend fun insert(data: List<Data>) = productsDao.insert(data)

    override fun fetchDataFromDB(): Flow<List<Data>> = productsDao.fetchProducts()

    override fun fetchAllPagedDB(): DataSource.Factory<Int, Data> = productsDao.getAllPaged()


}