package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface RoomTransactionRepository {
    fun observeTransactions(): Flow<List<Transaction>>

    suspend fun getUnsynced(): List<Transaction>

    suspend fun markAsSynced(ids: List<String>)

    suspend fun insert(transaction: Transaction): InsertResult

    suspend fun upsert(transactions: List<Transaction>)

    suspend fun getUpdatedAfter(time: Instant): List<Transaction>

    suspend fun getById(id: String?): Transaction?
}

enum class InsertResult {
    INSERTED,
    DUPLICATE
}