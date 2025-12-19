package com.knowledge.myfinapp.ui.expenselist

import java.math.BigDecimal
import java.time.LocalDate

data class UiExpense(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchant: String?,
    val category: String?,
    val occurredAt: LocalDate,
)
