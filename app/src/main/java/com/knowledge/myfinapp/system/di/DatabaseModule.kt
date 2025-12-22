package com.knowledge.myfinapp.system.di

import android.content.Context
import androidx.room.Room
import com.knowledge.myfinapp.data.database.AppDatabase
import com.knowledge.myfinapp.data.database.dao.CategoryDao
import com.knowledge.myfinapp.data.database.dao.TransactionDao
import com.knowledge.myfinapp.data.database.dao.MerchantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "myfin.db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideMerchantDao(db: AppDatabase): MerchantDao = db.merchantDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
}