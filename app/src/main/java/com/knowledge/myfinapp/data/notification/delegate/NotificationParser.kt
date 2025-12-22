package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.RawNotification
import com.knowledge.myfinapp.data.notification.parser.ParseResult

interface NotificationParser {
    fun parse(notification: RawNotification): ParseResult
}