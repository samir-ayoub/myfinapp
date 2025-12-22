package com.knowledge.myfinapp.data.repository

import androidx.room.withTransaction
import com.knowledge.myfinapp.data.mappers.toEntity
import com.knowledge.myfinapp.data.mappers.toDomain
import com.knowledge.myfinapp.data.database.dao.TransactionDao
import com.knowledge.myfinapp.data.database.AppDatabase
import com.knowledge.myfinapp.data.database.dao.CategoryDao
import com.knowledge.myfinapp.data.database.dao.MerchantDao
import com.knowledge.myfinapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class RoomTransactionRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val merchantDao: MerchantDao
    ): RoomTransactionRepository {

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeTransactions()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun getById(id: String?): Transaction? {
        return transactionDao.getById(id)?.toDomain()
    }

     override suspend fun markAsSynced(ids: List<String>) {
        transactionDao.markAsSynced(ids)
    }

    override suspend fun insert(transaction: Transaction): InsertResult {
        return db.withTransaction {
            transaction.category?.let { category ->
                categoryDao.insert(category.toEntity())
            }

            transaction.merchant?.let { merchant ->
                merchantDao.insert(merchant.toEntity())
            }

            val result = transactionDao.insert(
                transaction.toEntity(synced = false)
            )

            if (result > 0) {
                Timber.i("Transaction persisted ${transaction.id}")
                InsertResult.INSERTED
            } else {
                Timber.i("Duplicate transaction ignored ${transaction.id}")
                InsertResult.DUPLICATE
            }
        }
    }

    override suspend fun upsert(transactions: List<Transaction>) {
        transactions.forEach { transactionDao.upsert(it.toEntity(synced = true)) }
    }

    override suspend fun getUnsynced(): List<Transaction> {
        return transactionDao.unsynced().map { it.toDomain() }
    }

    override suspend fun getUpdatedAfter(time: Instant): List<Transaction> {
        TODO("Not yet implemented")
    }
}