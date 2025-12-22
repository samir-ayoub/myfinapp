package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.data.repository.RoomTransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTransactionUseCase @Inject constructor(
    private val roomTransactionRepository: RoomTransactionRepository,
) {
    operator fun invoke(): Flow<List<Transaction>> =
        roomTransactionRepository.observeTransactions()
}