package com.knowledge.myfinapp.data.model

import java.math.BigDecimal
import java.time.Instant

data class ParsedNotification(
    val bank: Bank,
    val amount: BigDecimal,
    val merchantRaw: String?,
    val occurredAt: Instant,
    val isDebit: Boolean,
    val evidences: ParsingEvidence
)

data class ParsingEvidence(
    val bankDetectedByPackageName: Boolean,
    val amountDetected: Boolean,
    val merchantDetected: Boolean
)