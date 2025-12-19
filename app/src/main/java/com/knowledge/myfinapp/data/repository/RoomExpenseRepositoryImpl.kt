package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.data.local.dao.ExpenseDao
import com.knowledge.myfinapp.data.mappers.local.toDomain
import com.knowledge.myfinapp.data.mappers.local.toEntity
import com.knowledge.myfinapp.data.mappers.relation.toDomain
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.repository.RoomExpenseRepository
import com.knowledge.myfinapp.domain.repository.InsertResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class RoomExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    ): RoomExpenseRepository {

    override fun observeExpenses(): Flow<List<Expense>> {
        return expenseDao.observeExpenses()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun getById(id: String?): Expense? {
        return expenseDao.getById(id)?.toDomain()
    }

     override suspend fun markAsSynced(ids: List<String>) {
        expenseDao.markAsSynced(ids)
    }

    override suspend fun insert(expense: Expense): InsertResult {
        val result = expenseDao.insert(expense.toEntity(synced = false))
        return if (result > 0) {
            Timber.i("Expense persisted ${expense.id}")
            InsertResult.INSERTED
        } else {
            Timber.i("Duplicate expense ignored ${expense.id}")
            InsertResult.DUPLICATE
        }
    }

    override suspend fun upsert(expenses: List<Expense>) {
        expenses.forEach { expenseDao.upsert(it.toEntity(synced = true)) }
    }

    override suspend fun getUnsynced(): List<Expense> {
        return expenseDao.unsynced().map { it.toDomain() }
    }

    override suspend fun getUpdatedAfter(time: Instant): List<Expense> {
        TODO("Not yet implemented")
    }
}