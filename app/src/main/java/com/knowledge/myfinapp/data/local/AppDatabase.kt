package com.knowledge.myfinapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.knowledge.myfinapp.data.local.dao.ExpenseDao
import com.knowledge.myfinapp.data.category.model.CategoryEntity
import com.knowledge.myfinapp.data.local.entity.ExpenseEntity
import com.knowledge.myfinapp.data.merchant.model.MerchantEntity
import com.knowledge.myfinapp.data.local.converter.InstantConverter
import com.knowledge.myfinapp.data.local.converter.BigDecimalConverter
import com.knowledge.myfinapp.data.local.dao.CategoryDao
import com.knowledge.myfinapp.data.local.dao.MerchantDao

@Database(
    entities = [
        ExpenseEntity::class,
        CategoryEntity::class,
        MerchantEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverter::class, BigDecimalConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun merchantDao(): MerchantDao
    abstract fun categoryDao(): CategoryDao
}