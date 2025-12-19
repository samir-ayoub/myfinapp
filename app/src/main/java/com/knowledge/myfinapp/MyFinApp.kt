package com.knowledge.myfinapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.knowledge.myfinapp.data.sync.scheduler.SyncInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
@HiltAndroidApp
class MyFinApp : Application(), Configuration.Provider {

    @Inject lateinit var syncInitializer: SyncInitializer
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        syncInitializer.init()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
