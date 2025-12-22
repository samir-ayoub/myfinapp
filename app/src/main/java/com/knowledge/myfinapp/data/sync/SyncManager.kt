package com.knowledge.myfinapp.data.sync

import android.content.Context
import com.knowledge.myfinapp.core.util.isNetworkAvailable
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.data.repository.TransactionRepository
import com.knowledge.myfinapp.data.repository.RoomTransactionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.Instant
import java.time.Instant.now
import javax.inject.Inject

class SyncManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val roomTransactionRepository: RoomTransactionRepository,
    private val repository: TransactionRepository,
    private val syncStore: SyncStore
) {
    suspend fun syncTransactions(): Unit {
        Timber.i("sync transactions called")

        if (!context.isNetworkAvailable()) return

        val unsyncedTransactions = roomTransactionRepository.getUnsynced()

        Timber.i("has unsynced transactions: ${unsyncedTransactions.isNotEmpty()}")

        if (pushUpdates(unsyncedTransactions)) {
            roomTransactionRepository.markAsSynced(
                unsyncedTransactions.map {
                    Timber.i("Marking ${it.merchant} transaction as synced")
                    it.id
                }
            )
        }

        val lastSync: Instant? = syncStore.lastSyncTime()
        val remoteUpdates = fetchRemoteUpdates(lastSync)

        Timber.i("merging remote transaction with local data")
        merge(remoteUpdates)

        syncStore.updateLastSync(now())
    }

    private suspend fun fetchRemoteUpdates(lastSync: Instant?): List<Transaction> {
        return repository.fetchRemoteTransactions(updatedAfter = lastSync)
    }
    private suspend fun pushUpdates(transactions: List<Transaction>): Boolean {
        if (transactions.isEmpty()) return false

        return repository.pushTransactions(transactions)
    }

    private suspend fun merge(remote: List<Transaction>) {
        val toUpsert = remote.filter { remoteTransaction ->
            val local = repository.getById(remoteTransaction.id)
            local == null || remoteTransaction.updatedAt > local.updatedAt
        }

        if (toUpsert.isNotEmpty()) {
            roomTransactionRepository.upsert(toUpsert)
        }
    }
}