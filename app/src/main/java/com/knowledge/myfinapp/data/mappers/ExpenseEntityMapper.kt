package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.model.ExpenseEntity
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.ExpenseSource
import java.time.Instant

fun ExpenseEntity.toDomain(): Expense =
    Expense(
        id = id,
        amount = amount,
        description = description,
        merchant = null, // ou resolver depois
        category = null,
        occurredAt = Instant.ofEpochMilli(occurredAt),
        source = ExpenseSource.valueOf(source),
        hash = hash,
        updatedAt = Instant.ofEpochMilli(updatedAt)
    )

fun Expense.toEntity(synced: Boolean): ExpenseEntity =
    ExpenseEntity(
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
