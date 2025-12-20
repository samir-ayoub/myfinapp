package com.knowledge.myfinapp.data.expenses.repository

import com.knowledge.myfinapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface RoomExpenseRepository {
    fun observeExpenses(): Flow<List<Expense>>

    suspend fun getUnsynced(): List<Expense>

    suspend fun markAsSynced(ids: List<String>)

    suspend fun insert(expense: Expense): InsertResult

    suspend fun upsert(expenses: List<Expense>)

    suspend fun getUpdatedAfter(time: Instant): List<Expense>

    suspend fun getById(id: String?): Expense?
}

enum class InsertResult {
    INSERTED,
    DUPLICATE
}