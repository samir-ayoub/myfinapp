package com.knowledge.myfinapp.data.sync.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.knowledge.myfinapp.data.sync.worker.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncInitializer @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun init() {
        val request =
            PeriodicWorkRequestBuilder<SyncWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "expense_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}