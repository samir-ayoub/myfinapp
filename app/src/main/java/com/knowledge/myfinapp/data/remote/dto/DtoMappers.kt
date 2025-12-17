package com.knowledge.myfinapp.data.remote.dto

import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.ExpenseSource
import com.knowledge.myfinapp.domain.model.Merchant
import java.time.Instant

fun ExpenseDto.toDomain(): Expense =
    Expense(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.toDomain(),
        category = category?.toDomain(),
        occurredAt = Instant.parse(occurredAt),
        hash = hash,
        updatedAt = Instant.parse(updatedAt),
        source = ExpenseSource.REMOTE
)

fun MerchantDto.toDomain(): Merchant =
    Merchant(id = id, name = name, normalizedName = normalizedName)

fun CategoryDto.toDomain(): Category =
    Category(id = id, name = name, color = color)