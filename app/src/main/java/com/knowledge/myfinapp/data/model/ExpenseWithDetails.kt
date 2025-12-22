package com.knowledge.myfinapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExpenseWithDetails(
    @Embedded val expense: ExpenseEntity,

    @Relation(
        parentColumn = "merchantId",
        entityColumn = "id"
    )
    val merchant: MerchantEntity?,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?
)