package com.ecom.sample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecom.sample.utils.DATABASE_VERSION
import com.ecom.sample.models.CartEntity
import com.ecom.sample.models.Product

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Product::class, CartEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao
}