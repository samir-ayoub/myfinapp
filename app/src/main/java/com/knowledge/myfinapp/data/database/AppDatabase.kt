package com.knowledge.myfinapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.knowledge.myfinapp.data.database.dao.TransactionDao
import com.knowledge.myfinapp.data.model.CategoryEntity
import com.knowledge.myfinapp.data.model.TransactionEntity
import com.knowledge.myfinapp.data.model.MerchantEntity
import com.knowledge.myfinapp.data.converters.InstantConverter
import com.knowledge.myfinapp.data.converters.BigDecimalConverter
import com.knowledge.myfinapp.data.database.dao.CategoryDao
import com.knowledge.myfinapp.data.database.dao.MerchantDao

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        MerchantEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverter::class, BigDecimalConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun merchantDao(): MerchantDao
    abstract fun categoryDao(): CategoryDao
}