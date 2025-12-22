package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface TransactionRepository {
    fun observeTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun getById(id: String): Transaction?

    // métodos remotos de sync
    suspend fun fetchRemoteTransactions(updatedAfter: Instant?): List<Transaction>
    suspend fun pushTransactions(transactions: List<Transaction>): Boolean
}