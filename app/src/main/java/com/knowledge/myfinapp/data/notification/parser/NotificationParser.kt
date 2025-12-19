package com.knowledge.myfinapp.data.notification.parser

import com.knowledge.myfinapp.data.notification.model.RawNotification

interface NotificationParser {
    fun parse(notification: RawNotification): ParseResult
}