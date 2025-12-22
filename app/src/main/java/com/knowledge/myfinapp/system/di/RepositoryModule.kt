package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.repository.TransactionRepositoryImpl
import com.knowledge.myfinapp.data.repository.RoomTransactionRepositoryImpl
import com.knowledge.myfinapp.data.repository.TransactionRepository
import com.knowledge.myfinapp.data.repository.RoomTransactionRepository
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
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindRoomTransactionRepository(
        impl: RoomTransactionRepositoryImpl
    ): RoomTransactionRepository
}