package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.model.TransactionWithDetails
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.TransactionSource
import java.time.Instant

fun TransactionWithDetails.toDomain(): Transaction =
    Transaction(
        id = transaction.id,
        amount = transaction.amount,
        description = transaction.description,
        merchant = merchant?.toDomain(),
        category = category?.toDomain(),
        occurredAt = Instant.ofEpochMilli(transaction.occurredAt),
        source = TransactionSource.valueOf(transaction.source),
        hash = transaction.hash,
        updatedAt = Instant.ofEpochMilli(transaction.updatedAt)
    )