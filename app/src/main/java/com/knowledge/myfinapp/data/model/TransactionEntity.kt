package com.knowledge.myfinapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.Companion.SET_NULL
        ),
        ForeignKey(
            entity = MerchantEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("merchantId"),
            onDelete = ForeignKey.Companion.SET_NULL
        )
    ],
    indices = [
        Index(value = ["hash"], unique = true),
        Index(value = ["categoryId"]),
        Index(value = ["merchantId"])
    ]
)
data class TransactionEntity(
    @PrimaryKey val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchantId: String?,
    val categoryId: String?,
    val occurredAt: Long,
    val source: String,
    val hash: String,
    val updatedAt: Long,
    val synced: Boolean
)