package com.knowledge.myfinapp.data.remote.dto

import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.TransactionSource
import com.knowledge.myfinapp.domain.model.Merchant
import java.time.Instant

fun TransactionDto.toDomain(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.toDomain(),
        category = category?.toDomain(),
        occurredAt = Instant.parse(occurredAt),
        hash = hash,
        updatedAt = Instant.parse(updatedAt),
        source = TransactionSource.REMOTE
)

fun MerchantDto.toDomain(): Merchant =
    Merchant(id = id, name = name, normalizedName = normalizedName)

fun CategoryDto.toDomain(): Category =
    Category(id = id, name = name, color = color)