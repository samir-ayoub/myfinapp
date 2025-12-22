package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.data.mappers.toDto
import com.knowledge.myfinapp.data.remote.api.ExpenseApi
import com.knowledge.myfinapp.data.remote.dto.toDomain
import com.knowledge.myfinapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseApi: ExpenseApi
): ExpenseRepository {
    override fun observeExpenses(): Flow<List<Expense>> {
        // Talvez combine com Room ou apenas retorno de cache se necessário
        return flow { emit(emptyList<Expense>()) }
    }

    override suspend fun addExpense(expense: Expense) {
        Timber.i("Posting expense $expense remotely")
        expenseApi.sendExpenses(listOf(expense.toDto()))
    }

    override suspend fun fetchRemoteExpenses(updatedAfter: Instant?): List<Expense> {
        Timber.i("Fetching expenses remotely")
        return emptyList()
//        return expenseApi.getExpenses(updatedAfter?.toEpochMilli()).map { it.toDomain() }
    }

    override suspend fun getById(id: String): Expense? {
        Timber.i("Fetching expenses for id $id")
        return expenseApi.getExpenseById(id)?.toDomain()
    }

    override suspend fun pushExpenses(expenses: List<Expense>): Boolean {
        try {
            Timber.i("Posting expenses $expenses remotely")
            expenseApi.sendExpenses(expenses.map { it.toDto() })
            return true
        } catch (error: Exception) {
            return false
        }
    }

}