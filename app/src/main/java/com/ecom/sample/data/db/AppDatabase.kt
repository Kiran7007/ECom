package com.ecom.sample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecom.sample.data.db.dao.CartDao
import com.ecom.sample.data.db.dao.ProductsDao
import com.ecom.sample.utils.DATABASE_VERSION
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.data.db.entity.Product

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Product::class, Cart::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao
    abstract fun cartDao(): CartDao
}