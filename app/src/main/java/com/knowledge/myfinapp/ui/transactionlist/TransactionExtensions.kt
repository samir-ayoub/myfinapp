package com.knowledge.myfinapp.ui.transactionlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.knowledge.myfinapp.domain.model.Transaction
import java.time.ZoneId

fun Transaction.toUiTransaction(): UiTransaction {
    val categoryName = category?.name ?: "Unknown"
    val zone = ZoneId.systemDefault()

    return UiTransaction(
        id = id,
        amount = amount,
        description = description,
        merchant = merchant?.name,
        occurredAt = occurredAt.atZone(zone).toLocalDate(),
        category = categoryName,
        categoryIcon = categoryName.toCategoryIcon()
    )
}

fun String.toCategoryIcon(): ImageVector {
    return when (this.lowercase()) {
        "food" -> Icons.Default.Fastfood
        "transport" -> Icons.Default.DirectionsCar
        "shopping" -> Icons.Default.ShoppingCart
        "health" -> Icons.Default.Favorite
        "salary" -> Icons.Default.AttachMoney
        else -> Icons.AutoMirrored.Default.Label
    }
}