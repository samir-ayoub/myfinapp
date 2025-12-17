package com.knowledge.myfinapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "expenses",
    indices = [
        Index(value = ["hash"], unique = true),
        Index(value = ["occurredAt"]),
        Index(value = ["updatedAt"])
    ]
)
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchantId: String?,
    val categoryId: String?,
    val occurredAt: Long,       // epoch millis
    val source: String,
    val hash: String,
    val updatedAt: Long,
    val synced: Boolean
)