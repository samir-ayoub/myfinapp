package com.knowledge.myfinapp.domain.model

import java.math.BigDecimal
import java.time.Instant

data class Expense(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchant: Merchant?,
    val category: Category?,
    val occurredAt: Instant,
    val source: ExpenseSource,
    val hash: String,
    val updatedAt: Instant
)
