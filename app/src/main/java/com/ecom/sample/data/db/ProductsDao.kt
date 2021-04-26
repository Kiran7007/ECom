package com.ecom.sample.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.ecom.sample.models.CartEntity
import com.ecom.sample.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: List<Product>): LongArray

    @Query("SELECT * FROM products")
    fun fetchProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products")
    fun getAllPaged(): DataSource.Factory<Int, Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart(cart: CartEntity?)

}