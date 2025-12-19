package com.knowledge.myfinapp.data.sync.scheduler

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.knowledge.myfinapp.data.sync.worker.SyncWorker
import java.util.concurrent.TimeUnit

class SyncInitializer: Initializer<Unit> {
    override fun create(context: Context) {
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

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}