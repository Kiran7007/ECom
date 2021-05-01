package com.ecom.sample.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecom.sample.data.db.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: List<Product>): LongArray

    @Query("SELECT * FROM products")
    fun fetchProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products")
    fun getAllPaged(): DataSource.Factory<Int, Product>
}