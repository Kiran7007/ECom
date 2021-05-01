package com.ecom.sample.data.db.dao

import androidx.room.*
import com.ecom.sample.data.db.entity.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: List<Cart>): LongArray

    @Query("SELECT * FROM cart")
    fun fetchCarts(): Flow<List<Cart>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: Cart)

    @Query("DELETE FROM cart")
    fun deleteAll()

    @Delete
    fun delete(cart: Cart)
}