package com.knowledge.myfinapp.ui.transactionlist

import androidx.compose.ui.graphics.vector.ImageVector
import java.math.BigDecimal
import java.time.LocalDate

data class UiTransaction(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchant: String?,
    val occurredAt: LocalDate,
    val category: String?,
    val categoryIcon: ImageVector,
)
