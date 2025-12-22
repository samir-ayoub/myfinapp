package com.knowledge.myfinapp.data.sync.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.knowledge.myfinapp.data.sync.worker.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SyncTrigger @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun triggerNow() {
        val request =
            OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "transaction_sync_now",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }
}