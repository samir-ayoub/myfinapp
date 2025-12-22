package com.knowledge.myfinapp.domain.usecase

import com.knowledge.myfinapp.data.CategoryResolver
import com.knowledge.myfinapp.data.sync.scheduler.SyncTrigger
import com.knowledge.myfinapp.data.repository.RoomTransactionRepository
import com.knowledge.myfinapp.data.MerchantResolver
import com.knowledge.myfinapp.data.model.ParsedNotification
import com.knowledge.myfinapp.data.notification.delegate.TransactionBuilder
import timber.log.Timber
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val categoryResolver: CategoryResolver,
    private val merchantResolver: MerchantResolver,
    private val transactionBuilder: TransactionBuilder,
    private val roomTransactionRepository: RoomTransactionRepository,
    private val syncTrigger: SyncTrigger
) {
    suspend operator fun invoke(parsedData: ParsedNotification) {
        Timber.i("Registering new transaction: $parsedData")

        val category = categoryResolver.resolve(parsedData.merchantRaw)
        val merchant = merchantResolver.resolve(parsedData.merchantRaw)
        val transaction = transactionBuilder.build(
            data = parsedData,
            category = category,
            merchant = merchant
        )

        Timber.i("New transaction registered: ${transaction.id}")
        roomTransactionRepository.insert(transaction)
        Timber.i("requesting sync trigger")
        syncTrigger.triggerNow()
    }
}