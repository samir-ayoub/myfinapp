package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.data.local.dao.ExpenseDao
import com.knowledge.myfinapp.data.mappers.local.toEntity
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.repository.ExpenseSyncRepository
import java.time.Instant

class ExpenseSyncRepositoryImpl(
    private val expenseDao: ExpenseDao,
    ): ExpenseSyncRepository {
    override suspend fun getById(id: String?): Expense? {
        TODO("Not yet implemented")
    }

     override suspend fun markAsSynced(ids: List<String>) {
        expenseDao.markAsSynced(ids)
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