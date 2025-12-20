package com.knowledge.myfinapp.data.entities.mappers.remote

import com.knowledge.myfinapp.data.category.mapper.toDto
import com.knowledge.myfinapp.data.remote.dto.ExpenseDto
import com.knowledge.myfinapp.domain.model.Expense

fun Expense.toDto(): ExpenseDto =
    ExpenseDto(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.toDto(),
        category = category?.toDto(),
        occurredAt = occurredAt.toString(),
        hash = hash,
        updatedAt = updatedAt.toString()
    )