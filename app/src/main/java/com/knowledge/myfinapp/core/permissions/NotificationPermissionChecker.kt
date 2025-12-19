package com.knowledge.myfinapp.core.permissions

import android.app.Application
import android.provider.Settings
import javax.inject.Inject

class NotificationPermissionChecker @Inject constructor(
    private val app: Application
) {
    fun hasPermission(): Boolean {
        val enabledListeners = Settings.Secure.getString(
            app.contentResolver,
            "enabled_notification_listeners"
        )
        return enabledListeners?.contains(app.packageName) == true
    }
}
