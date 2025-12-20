package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.expenses.repository.ExpenseRepositoryImpl
import com.knowledge.myfinapp.data.expenses.repository.RoomExpenseRepositoryImpl
import com.knowledge.myfinapp.data.expenses.repository.ExpenseRepository
import com.knowledge.myfinapp.data.expenses.repository.RoomExpenseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        impl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindExpenseSyncRepository(
        impl: RoomExpenseRepositoryImpl
    ): RoomExpenseRepository
}