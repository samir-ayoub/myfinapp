package com.knowledge.myfinapp.data.remote.dto

import java.math.BigDecimal

data class TransactionDto(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val merchant: MerchantDto?,
    val category: CategoryDto?,
    val occurredAt: String,     // ISO-8601
    val hash: String,
    val updatedAt: String
)