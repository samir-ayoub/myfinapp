package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.data.CategoryResolver
import com.knowledge.myfinapp.data.sync.scheduler.SyncTrigger
import com.knowledge.myfinapp.data.repository.RoomExpenseRepository
import com.knowledge.myfinapp.data.MerchantResolver
import com.knowledge.myfinapp.data.model.ParsedExpenseData
import com.knowledge.myfinapp.data.notification.delegate.ExpenseBuilder
import timber.log.Timber
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val categoryResolver: CategoryResolver,
    private val merchantResolver: MerchantResolver,
    private val expenseBuilder: ExpenseBuilder,
    private val expenseLocalRepository: RoomExpenseRepository,
    private val syncTrigger: SyncTrigger
) {
    suspend operator fun invoke(parsedData: ParsedExpenseData) {
        Timber.i("Registering new expense: $parsedData")

        val category = categoryResolver.resolve(parsedData.merchantRaw)
        val merchant = merchantResolver.resolve(parsedData.merchantRaw)
        val expense = expenseBuilder.build(
            data = parsedData,
            category = category,
            merchant = merchant
        )

        Timber.i("New expense registered: ${expense.id}")
        expenseLocalRepository.insert(expense)
        Timber.i("requesting sync trigger")
        syncTrigger.triggerNow()
    }
}