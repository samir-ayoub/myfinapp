package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.model.TransactionEntity
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.TransactionSource
import java.time.Instant

fun TransactionEntity.toDomain(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        description = description,
        merchant = null, // ou resolver depois
        category = null,
        occurredAt = Instant.ofEpochMilli(occurredAt),
        source = TransactionSource.valueOf(source),
        hash = hash,
        updatedAt = Instant.ofEpochMilli(updatedAt)
    )

fun Transaction.toEntity(synced: Boolean): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        description = description,
        merchantId = merchant?.id,
        categoryId = category?.id,
        occurredAt = occurredAt.toEpochMilli(),
        source = source.name,
        hash = hash,
        updatedAt = updatedAt.toEpochMilli(),
        synced = synced
    )
