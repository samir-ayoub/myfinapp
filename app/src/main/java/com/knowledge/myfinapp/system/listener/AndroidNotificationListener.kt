package com.knowledge.myfinapp.system.listener

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.knowledge.myfinapp.data.notification.listener.NotificationDispatcher
import com.knowledge.myfinapp.data.notification.model.RawNotification
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import timber.log.Timber
import java.time.Instant

@AndroidEntryPoint
class AndroidNotificationListener : NotificationListenerService() {

    @Inject lateinit var notificationDispatcher: NotificationDispatcher

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Timber.i("New notification detected. Parsing...")
        sbn ?: return

        val rawNotification = sbn.toRawNotification() ?: return

        notificationDispatcher.dispatch(rawNotification)
    }


    fun StatusBarNotification.toRawNotification(): RawNotification? {
        val text = notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: return null
        val title = notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
        val postedAt = Instant.ofEpochMilli(postTime)
        val rawNotification = RawNotification(
            packageName = packageName,
            title = title,
            text = text,
            postedAt = postedAt
        )

        Timber.i("mapped raw notification: $rawNotification")

        return rawNotification
    }
}