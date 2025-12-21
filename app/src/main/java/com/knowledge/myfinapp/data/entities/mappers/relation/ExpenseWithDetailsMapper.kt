package com.knowledge.myfinapp.data.entities.mappers.relation

import com.knowledge.myfinapp.data.local.relation.ExpenseWithDetails
import com.knowledge.myfinapp.data.category.mapper.toDomain
import com.knowledge.myfinapp.data.merchant.mapper.toDomain
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.ExpenseSource
import java.time.Instant

fun ExpenseWithDetails.toDomain(): Expense =
    Expense(
        id = expense.id,
        amount = expense.amount,
        description = expense.description,
        merchant = merchant?.toDomain(),
        category = category?.toDomain(),
        occurredAt = Instant.ofEpochMilli(expense.occurredAt),
        source = ExpenseSource.valueOf(expense.source),
        hash = expense.hash,
        updatedAt = Instant.ofEpochMilli(expense.updatedAt)
    )