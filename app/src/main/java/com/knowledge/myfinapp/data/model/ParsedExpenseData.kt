package com.knowledge.myfinapp.data.model

import java.math.BigDecimal
import java.time.Instant

data class ParsedExpenseData(
    val bank: Bank,
    val amount: BigDecimal,
    val merchantRaw: String?,
    val occurredAt: Instant,
    val isDebit: Boolean
)