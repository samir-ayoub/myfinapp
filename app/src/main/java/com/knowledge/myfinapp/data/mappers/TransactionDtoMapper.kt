package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.remote.dto.TransactionDto
import com.knowledge.myfinapp.domain.model.Transaction

fun Transaction.toDto(): TransactionDto =
    TransactionDto(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.toDto(),
        category = category?.toDto(),
        occurredAt = occurredAt.toString(),
        hash = hash,
        updatedAt = updatedAt.toString()
    )