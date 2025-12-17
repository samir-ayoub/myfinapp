package com.knowledge.myfinapp.data.notification.model

import java.time.Instant

data class RawNotification(
    val packageName: String,
    val title: String?,
    val text: String,
    val postedAt: Instant
)