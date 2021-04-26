package com.example.emanditask.dtatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emanditask.helpers.DATABASE_VERSION
import com.example.emanditask.models.CartEntity
import com.example.emanditask.models.Data
import com.example.emanditask.models.ProductModel

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Data::class, CartEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao
}