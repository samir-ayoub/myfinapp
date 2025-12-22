package com.knowledge.myfinapp.data.sync

import android.content.Context
import com.knowledge.myfinapp.core.util.isNetworkAvailable
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.data.repository.ExpenseRepository
import com.knowledge.myfinapp.data.repository.RoomExpenseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.time.Instant
import java.time.Instant.now
import javax.inject.Inject

class SyncManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val expenseLocalRepository: RoomExpenseRepository,  // para operações de Room
    private val repository: ExpenseRepository,          // para lógica de domínio se precisar
    private val syncStore: SyncStore
) {
    suspend fun syncExpenses(): Unit {
        Timber.i("sync expenses called")

        if (!context.isNetworkAvailable()) return

        val unsyncedExpenses = expenseLocalRepository.getUnsynced()

        Timber.i("has unsynced expenses: ${unsyncedExpenses.isNotEmpty()}")

        if (pushUpdates(unsyncedExpenses)) {
            expenseLocalRepository.markAsSynced(
                unsyncedExpenses.map {
                    Timber.i("Marking ${it.merchant} expense as synced")
                    it.id
                }
            )
        }

        val lastSync: Instant? = syncStore.lastSyncTime()
        val remoteUpdates = fetchRemoteUpdates(lastSync)

        Timber.i("merging remote expenses with local data")
        merge(remoteUpdates)

        syncStore.updateLastSync(now())
    }

    private suspend fun fetchRemoteUpdates(lastSync: Instant?): List<Expense> {
        return repository.fetchRemoteExpenses(updatedAfter = lastSync)
    }
    private suspend fun pushUpdates(expenses: List<Expense>): Boolean {
        if (expenses.isEmpty()) return false

        return repository.pushExpenses(expenses)
    }

    private suspend fun merge(remote: List<Expense>) {
        val toUpsert = remote.filter { remoteExpense ->
            val local = repository.getById(remoteExpense.id)
            local == null || remoteExpense.updatedAt > local.updatedAt
        }

        if (toUpsert.isNotEmpty()) {
            expenseLocalRepository.upsert(toUpsert)
        }
    }
}