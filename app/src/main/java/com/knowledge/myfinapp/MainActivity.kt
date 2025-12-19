package com.knowledge.myfinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.knowledge.myfinapp.data.sync.scheduler.SyncScheduler
import com.knowledge.myfinapp.ui.AppEntry
import com.knowledge.myfinapp.ui.theme.MyFinAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var syncScheduler: SyncScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        syncScheduler.schedulePeriodic()

        enableEdgeToEdge()

        setContent {
            MyFinAppTheme {
                AppEntry()
            }
        }
    }
}
