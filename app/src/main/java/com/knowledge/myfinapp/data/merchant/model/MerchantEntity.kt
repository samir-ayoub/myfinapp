package com.knowledge.myfinapp.data.merchant.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "merchants",
    indices = [Index(value = ["normalizedName"], unique = true)]
)
data class MerchantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val normalizedName: String
)