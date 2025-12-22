package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.data.repository.RoomExpenseRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase @Inject constructor(
    private val roomExpenseRepository: RoomExpenseRepository,
) {
    operator fun invoke(): Flow<List<Expense>> =
        roomExpenseRepository.observeExpenses()
}