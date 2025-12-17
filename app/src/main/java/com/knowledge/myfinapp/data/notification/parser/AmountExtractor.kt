package com.knowledge.myfinapp.data.notification.parser

import java.math.BigDecimal

interface AmountExtractor {
    fun extract(text: String): BigDecimal?
}