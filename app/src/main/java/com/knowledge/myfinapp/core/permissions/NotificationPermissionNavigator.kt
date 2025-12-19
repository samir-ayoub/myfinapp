package com.knowledge.myfinapp.core.permissions

import android.app.Application
import android.content.Intent
import android.provider.Settings
import javax.inject.Inject

class NotificationPermissionNavigator @Inject constructor(private val app: Application) {
    fun openSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }
}
