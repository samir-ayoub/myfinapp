package com.knowledge.myfinapp.data.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.knowledge.myfinapp.data.sync.SyncManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncManager: SyncManager
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.i("Starting sync")
        return try {
            syncManager.syncExpenses()
            Timber.i("Sync succeeded")
            return Result.success()
        } catch (t: Throwable) {
            Timber.e("Sync failed due to $t")
            Result.retry()
        }
    }
}