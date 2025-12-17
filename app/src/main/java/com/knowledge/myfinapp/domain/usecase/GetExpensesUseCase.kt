package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.repository.ExpenseRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> =
        repository.observeExpenses()
}