package com.knowledge.myfinapp.data.notification.delegate

import java.math.BigDecimal

interface AmountExtractor {
    fun extract(text: String): BigDecimal?
}