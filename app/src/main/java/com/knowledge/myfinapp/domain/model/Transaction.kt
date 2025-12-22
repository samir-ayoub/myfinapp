package com.knowledge.myfinapp.domain.model

import java.math.BigDecimal
import java.time.Instant

data class Transaction(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchant: Merchant?,
    val category: Category?,
    val occurredAt: Instant,
    val source: TransactionSource,
    val hash: String,
    val updatedAt: Instant
)

enum class TransactionType {
    EXPENSE, INCOME
}

enum class TransactionSource {
    NOTIFICATION,
    MANUAL,
    REMOTE
}