package com.knowledge.myfinapp.ui.expenselist

import com.knowledge.myfinapp.domain.model.Expense
import java.time.ZoneId

fun Expense.toUiExpense(): UiExpense {
    val zone = ZoneId.systemDefault()
    return UiExpense(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.name,
        category = category?.name,
        occurredAt = occurredAt.atZone(zone).toLocalDate()
    )
}