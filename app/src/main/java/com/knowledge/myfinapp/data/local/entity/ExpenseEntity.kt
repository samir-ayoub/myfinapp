package com.knowledge.myfinapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.math.BigDecimal
import com.knowledge.myfinapp.data.category.model.CategoryEntity

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = MerchantEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("merchantId"),
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["hash"], unique = true),
        Index(value = ["categoryId"]),
        Index(value = ["merchantId"])
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