package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.data.local.dao.ExpenseDao
import com.knowledge.myfinapp.data.mappers.local.toDomain
import com.knowledge.myfinapp.data.mappers.local.toEntity
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.repository.ExpenseLocalRepository
import com.knowledge.myfinapp.domain.repository.InsertResult
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class ExpenseLocalRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    ): ExpenseLocalRepository {
    override suspend fun getById(id: String?): Expense? {
        return expenseDao.getById(id)?.toDomain()
    }

     override suspend fun markAsSynced(ids: List<String>) {
        expenseDao.markAsSynced(ids)
    }

    override suspend fun insert(expense: Expense): InsertResult {
        val result = expenseDao.insert(expense.toEntity(synced = false))
        return if (result > 0) {
            InsertResult.INSERTED
        } else {
            InsertResult.DUPLICATE
        }
    }

    override suspend fun upsert(expenses: List<Expense>) {
        expenses.forEach { expenseDao.upsert(it.toEntity(synced = true)) }
    }

    override suspend fun getUnsynced(): List<Expense> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpdatedAfter(time: Instant): List<Expense> {
        TODO("Not yet implemented")
    }
}