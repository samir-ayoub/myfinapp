package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.data.sync.scheduler.SyncTrigger
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.repository.ExpenseLocalRepository
import timber.log.Timber
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
    private val syncTrigger: SyncTrigger
) {
    suspend operator fun invoke(expense: Expense) {
        Timber.i("registering new expense locally")
        expenseLocalRepository.insert(expense)
        Timber.i("requesting sync trigger")
        syncTrigger.triggerNow()
    }
}