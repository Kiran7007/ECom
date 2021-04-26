package com.example.emanditask.dtatabase

import androidx.paging.DataSource
import androidx.room.*
import com.example.emanditask.models.CartEntity
import com.example.emanditask.models.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(peoples: List<Data>): LongArray

    @Query("SELECT * FROM products")
    fun fetchProducts(): Flow<List<Data>>

    @Query("SELECT * FROM products")
    fun getAllPaged(): DataSource.Factory<Int, Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart(cart: CartEntity?)

}