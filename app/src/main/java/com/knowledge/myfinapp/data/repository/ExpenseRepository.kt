package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface ExpenseRepository {
    fun observeExpenses(): Flow<List<Expense>>
    suspend fun addExpense(expense: Expense)
    suspend fun getById(id: String): Expense?

    // métodos remotos de sync
    suspend fun fetchRemoteExpenses(updatedAfter: Instant?): List<Expense>
    suspend fun pushExpenses(expenses: List<Expense>): Boolean
}