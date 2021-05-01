package com.ecom.sample.data.repository

import androidx.paging.DataSource
import com.ecom.sample.data.db.dao.CartDao
import com.ecom.sample.data.db.dao.ProductsDao
import com.ecom.sample.data.db.entity.Product
import com.ecom.sample.data.remote.ApiService
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.models.Result
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productsDao: ProductsDao,
    private val cartDao: CartDao
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

    override suspend fun insertCartList(data: List<Cart>) = cartDao.insert(data)

    override fun fetchDataFromDB() = productsDao.fetchProducts()

    override fun fetchAllPagedDB() = productsDao.getAllPaged()

    override fun clearAllCartItems() = cartDao.deleteAll()
}