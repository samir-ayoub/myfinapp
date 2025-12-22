package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.data.mappers.toDto
import com.knowledge.myfinapp.data.remote.api.TransactionApi
import com.knowledge.myfinapp.data.remote.dto.toDomain
import com.knowledge.myfinapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApi: TransactionApi
): TransactionRepository {
    override fun observeTransactions(): Flow<List<Transaction>> {
        // Talvez combine com Room ou apenas retorno de cache se necessário
        return flow { emit(emptyList<Transaction>()) }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        Timber.i("Posting transaction $transaction remotely")
        transactionApi.sendTransactions(listOf(transaction.toDto()))
    }

    override suspend fun fetchRemoteTransactions(updatedAfter: Instant?): List<Transaction> {
        Timber.i("Fetching transactions remotely")
        return emptyList()
//        return transactionApi.getTransactions(updatedAfter?.toEpochMilli()).map { it.toDomain() }
    }

    override suspend fun getById(id: String): Transaction? {
        Timber.i("Fetching transactions for id $id")
        return transactionApi.getTransactionById(id)?.toDomain()
    }

    override suspend fun pushTransactions(transactions: List<Transaction>): Boolean {
        try {
            Timber.i("Posting transactions $transactions remotely")
            transactionApi.sendTransactions(transactions.map { it.toDto() })
            return true
        } catch (error: Exception) {
            return false
        }
    }

}